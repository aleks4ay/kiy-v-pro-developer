package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.model.*;
import org.aleks4ay.developer.tools.FileWriter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ParsingEngine {

    ConnectionPool connectionPool = ConnectionPool.getInstance();
    OrderService orderService = new OrderService(new OrderDao(connectionPool));
    OrderTimeService orderTimeService = new OrderTimeService(new OrderTimeDao(connectionPool));
    DescriptionService descriptionService = new DescriptionService(new DescriptionDao(connectionPool));
    DescriptionTimeService descriptionTimeService = new DescriptionTimeService(new DescriptionTimeDao(connectionPool));


    public List<Order> getOrdersWithDescriptions(Page page, String sort) {
        String sortOrder;
        Comparator<Order> comparing;

        if (sort.equalsIgnoreCase("По номеру заказа")) {
            sortOrder = " order by j.doc_number;";
            comparing = Comparator.comparing(Order::getDocNumber);
        } else {
            sortOrder = " order by o.t_factory;";
            comparing = Comparator.comparing(Order::getDateToFactory);
        }

        Map<String, Order> orderMap = findAllAsMap(page, sortOrder);
        for (Description d : descriptionService.findAllNew()) {
            String key = d.getIdOrder();
            if (orderMap.containsKey(key)) {
                orderMap.get(key).getDescriptions().add(d);
            }
        }
        return orderMap.values().stream()
                .filter(order -> order.getDescriptions().size() > 0)
                .sorted(comparing)
                .collect(Collectors.toList());
    }


    public Map<String, Order> findAllAsMap(Page page, String sort) {
        List<Order> orders = orderService.findAllParsing(sort);
        page.setSize(orders.size());
        if (page.getPosition() > page.getMaxPosition()) {
            return Collections.emptyMap();
        }
        return orders.stream()
                .skip(page.getPosition() * page.getPositionOnPage())
                .limit(page.getPositionOnPage())
                .collect(Collectors.toMap(Order::getId, order -> order));
    }

    public void setType (List<DescriptionParsing> listParsing) {
        List<DescriptionTime> times = new ArrayList<>();
        int newType = 0;
        TypeName typeName = null;
        for (DescriptionParsing descr : listParsing) {

            if (descr.getButtonKB().isSelected()) {
                typeName = TypeName.KB;
                descr.setStatus(StatusName.KB_NEW.toString());
            } else if (descr.getButtonFactory().isSelected()) {
                typeName = TypeName.FACTORY;
                descr.setStatus(StatusName.FACTORY.toString());
            } else if (descr.getButtonTeh().isSelected()) {
                typeName = TypeName.TECHNO;
                descr.setStatus(StatusName.NOT_TRACKED.toString());
            } else if (descr.getButtonOther().isSelected()) {
                typeName = TypeName.OTHER;
                descr.setStatus(StatusName.NOT_TRACKED.toString());
            }
            boolean resultSaving;
            if (typeName != null) {
                DescriptionTime time = new DescriptionTime(descr.getId(), descr.getStatus(), LocalDateTime.now());
                times.add(time);
                resultSaving = saveDescriptionType(descr, typeName);
                if (resultSaving) {
                    newType ++;
                }
            }

        }
        if (newType == listParsing.size()) {
            String orderId = listParsing.get(0).getId().split("-")[0];
            DescriptionTime minDescriptionTime = getMinimumDescriptionTime(times);
            OrderTime minOrderTime = new OrderTime(minDescriptionTime);

            orderService.updateStatus(orderId, minOrderTime.getStatusName());
            orderTimeService.create(minOrderTime);
        }
        if (newType == listParsing.size()) {
            FileWriter.writeTimeChange("par");
        }
    }

    private boolean saveDescriptionType(DescriptionParsing descr, TypeName typeName) {
        LocalDateTime statusTime = LocalDateTime.now();
        DescriptionTime time = new DescriptionTime(descr.getId(), descr.getStatus(), statusTime);
        boolean b1 = descriptionTimeService.create(time);
        boolean b2 = descriptionService.updateStatusName(descr.getId(), descr.getStatus());
        boolean b3 = descriptionService.updateTypeName(descr.getId(), typeName.toString());
        return b1 & b2 & b3;
    }

    private DescriptionTime getMinimumDescriptionTime(List<DescriptionTime> list) {
        return list.stream()
                .min(DescriptionTime::compareTo)
                .get();
    }
}
