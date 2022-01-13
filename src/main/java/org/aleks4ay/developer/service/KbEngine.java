package org.aleks4ay.developer.service;

import javafx.scene.control.Alert;
import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.model.*;
import org.aleks4ay.developer.tools.FileWriter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class KbEngine {

    ConnectionPool connectionPool = ConnectionPool.getInstance();
    OrderService orderService = new OrderService(new OrderDao(connectionPool));
    OrderTimeService orderTimeService = new OrderTimeService(new OrderTimeDao(connectionPool));
    DescriptionService descriptionService = new DescriptionService(new DescriptionDao(connectionPool));
    DescriptionTimeService descriptionTimeService = new DescriptionTimeService(new DescriptionTimeDao(connectionPool));


    public List<Order> getOrdersWithDescriptionsKb(Page page, String sort) {
        String sortOrder;
        Comparator<Order> comparing;

        if (sort.equalsIgnoreCase("по № заказа")) {
            sortOrder = " order by j.doc_number;";
            comparing = Comparator.comparing(Order::getDocNumber);
        } else {
            sortOrder = " order by o.t_factory;";
            comparing = Comparator.comparing(Order::getDateToFactory);
        }

        Map<String, String> designerPseudoName = descriptionService.getDesignerPseudoNames();
        Map<String, Order> orderMap = findAllAsMap(page, sortOrder);
        for (Description d : descriptionService.findAllKb()) {
            if (designerPseudoName.containsKey(d.getDesigner())) {
                d.setDesigner(designerPseudoName.get(d.getDesigner()));
            }
            String key = d.getIdOrder();
            if (orderMap.containsKey(key)) {
                orderMap.get(key).getDescriptions().add(d);
            }
        }
        return orderMap.values().stream()
                .sorted(comparing)
                .collect(Collectors.toList());
    }

    public List<Order> getOrdersWithDescriptionsFind(String yearText, String numberText) {
        Map<String, String> designerPseudoName = descriptionService.getDesignerPseudoNames();
        Map<String, Order> orderMap = orderService.findAllLike(yearText, numberText);

        for (Description d : descriptionService.findAll()) {
            if (designerPseudoName.containsKey(d.getDesigner())) {
                d.setDesigner(designerPseudoName.get(d.getDesigner()));
            }
            String key = d.getIdOrder();
            if (orderMap.containsKey(key)) {
                orderMap.get(key).getDescriptions().add(d);
            }
        }
        return new ArrayList<>(orderMap.values());
    }

    public Map<String, Order> findAllAsMap(Page page, String sort) {
        List<Order> orders = orderService.findAllKb(sort);
        page.setSize(orders.size());
        if (page.getPosition() > page.getMaxPosition()) {
            return Collections.emptyMap();
        }
        return orders.stream()
                .skip(page.getPosition() * page.getPositionOnPage())
                .limit(page.getPositionOnPage())
                .collect(Collectors.toMap(Order::getId, order -> order));
    }

    public void setStatus (List<DescriptionKb> listKb) {
        List<DescriptionTime> times = new ArrayList<>();
        LocalDateTime newTime = LocalDateTime.now();
        Map<String, StatusName> statusMap = new HashMap<>();
        Map<String, String> designerMap = new HashMap<>();
        for (DescriptionKb descr : listKb) {
            if (descr.getCheckBoxKbStart().isSelected() && descr.isNewStatusBigger(StatusName.KB_START)) {
                descr.setStatus(StatusName.KB_START.toString());
                addNewStatus(times, newTime, descr, statusMap);
                if (System.getenv().containsKey("USERNAME")) {
                    descr.setDesigner(System.getenv().get("USERNAME"));
                }
                else {
                    descr.setDesigner(System.getProperty("user.name"));
                }
                designerMap.put(descr.getId(), descr.getDesigner());
            }
            if (descr.getCheckBoxKbQuestion().isSelected()) {
                if (setStatusIfEnable(StatusName.KB_QUESTION, descr)) {
                    addNewStatus(times, newTime, descr, statusMap);
                }
            }
            if (descr.getCheckBoxKbContinued().isSelected()) {
                if (setStatusIfEnable(StatusName.KB_CONTINUED, descr)) {
                    addNewStatus(times, newTime, descr, statusMap);
                }
            }
            if (descr.getCheckBoxKbEnd().isSelected()) {
                if (setStatusIfEnable(StatusName.FACTORY, descr)) {
                    addNewStatus(times, newTime, descr, statusMap);
                }
            }
        }

        if (!times.isEmpty()) {

            saveNewDescriptionStatus(times, statusMap);

            saveNewOrderStatus(listKb, times);

            saveNewDesigner(designerMap);

            FileWriter.writeTimeChange("kb");
        }
    }

    private boolean setStatusIfEnable(StatusName statusName, DescriptionKb descr) {
        if (descr.getDesigner() == null || descr.isNewStatusLess(statusName)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("Заказ еще не взят в работу конструктором!" + System.lineSeparator() +
                    "или находится на более поздней стадии");
            alert.showAndWait();
            return false;
        } else {
            descr.setStatus(statusName.toString());
            return true;
        }
    }

    private void saveNewDesigner(Map<String, String> designerMap) {
        Map<String, String> designerPseudoName = descriptionService.getDesignerPseudoNames();
        if (!designerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : designerMap.entrySet()) {
                if (! designerPseudoName.containsKey(entry.getValue())) {
                    descriptionService.createPseudoName(entry.getValue(), entry.getValue());
                }
                descriptionService.updateDesignerName(entry.getKey(), entry.getValue());
            }
        }
    }

    private void addNewStatus(List<DescriptionTime> times, LocalDateTime newTime, DescriptionKb descr, Map<String, StatusName> statusMap) {
        DescriptionTime time = new DescriptionTime(descr.getId(), descr.getStatus(), newTime);
        statusMap.put(descr.getId(), StatusName.valueOf(descr.getStatus()));
        times.add(time);
        descr.getTimes().add(time);
    }

    private void saveNewDescriptionStatus(List<DescriptionTime> times, Map<String, StatusName> statusMap) {
        for (DescriptionTime dt : times) {
            descriptionTimeService.create(dt);
            if (statusMap.get(dt.getIdDescription()).equals(dt.getStatusName())) {
                descriptionService.updateStatusName(dt.getIdDescription(), dt.getStatusName().toString());
            }
        }
    }

    private void saveNewOrderStatus(List<DescriptionKb> kbList, List<DescriptionTime> timeList) {
        String orderId = kbList.get(0).getId().split("-")[0];
        StatusName minStatus = getMinStatus(kbList);
        DescriptionTime minNewDescriptionTime = getMinNewDescriptionTime(timeList);
        if (minNewDescriptionTime.getStatusName().getStatusIndex() == minStatus.getStatusIndex()) {
            orderService.updateStatus(orderId, minNewDescriptionTime.getStatusName());
            orderTimeService.create(new OrderTime(minNewDescriptionTime));
        }
    }


    private DescriptionTime getMinNewDescriptionTime(List<DescriptionTime> times) {
        return times.stream()
                .min(DescriptionTime::compareTo)
                .get();
    }

    private StatusName getMinStatus(List<DescriptionKb> listKb) {
        return listKb.stream()
                .map(d -> StatusName.valueOf(d.getStatus()))
                .min(StatusName::compareTo)
                .get();
    }
}
