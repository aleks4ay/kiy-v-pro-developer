package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class OrderService extends AbstractService<Order> {

    ConnectionPool connectionPool = ConnectionPool.getInstance();
    OrderTimeService orderTimeService = new OrderTimeService(new OrderTimeDao(connectionPool));
    DescriptionService descriptionService = new DescriptionService(new DescriptionDao(connectionPool));

    public OrderService(BaseDao<Order> orderDao) {
        super(orderDao);
    }

    public boolean updateStatus(String orderId, StatusName status) {
        return ((OrderDao)getDao()).updateStatusName(orderId, status.toString());
    }

    public List<Order> findAllKb(SortWay sortWay) {
        return ((OrderDao)getDao()).findAllKb(sortWay.getSql());
    }

    public List<Order> findAllParsing() {
        return ((OrderDao)getDao()).findAllParsing();
    }

    public Map<String, Order> findAllLike(String yearText, String numberText) {
        return ((OrderDao)getDao()).findAllLike(yearText, numberText)
                .stream()
                .collect(Collectors.toMap(Order::getId, o -> o));
    }

/*    public Map<String, Order> findAllByStatusesLike(String statuses, String numbers) {
        return ((OrderDao)getDao()).findAllByStatuses(statuses, numbers)
                .stream()
                .collect(Collectors.toMap(Order::getId, o -> o));
    }*/




    public List<Order> getOrdersWithDescriptionsKb(Page page, SortWay sort) {

        Map<String, String> designerPseudoName = descriptionService.getDesignerPseudoNames();

        List<Order> orders = findAllKb(sort);
        Map<String, Order> orderMap = findAllAsMap(orders, page, sort);

        fillOrdersWithTimes(orderMap);

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
                .sorted(Order.getComparator(sort))
                .collect(Collectors.toList());
    }

    private void fillOrdersWithTimes(Map<String, Order> orderMap) {
        Map<String, List<OrderTime>> orderTimeMap = orderTimeService.findAllAsMap();
        for (Order o : orderMap.values()) {
            o.setTimes(orderTimeMap.get(o.getId()));
        }
    }

    public Map<String, Order> findAllAsMap(List<Order> orders, Page page, SortWay sort) {

        page.setSize(orders.size());
        if (page.getPosition() > page.getMaxPosition()) {
            return Collections.emptyMap();
        }
        return orders.stream()
                .skip(page.getPosition() * page.getPositionOnPage())
                .limit(page.getPositionOnPage())
                .collect(Collectors.toMap(Order::getId, order -> order));
    }


    public List<Order> getOrdersWithDescriptionsFind(String yearText, String numberText) {

        Map<String, Order> orderMap = findAllLike(yearText, numberText);

        fillOrdersWithTimes(orderMap);
        fillOrdersWithDescriptions(orderMap);

        return new ArrayList<>(orderMap.values());
    }

    public List<Order> getOrdersWithDescriptionsManager(Page page, SortWay sort, Set<StatusName> orderToShow, String numbers) {
        String statuses = getStatuses(orderToShow);

        List<Order> orders = ((OrderDao)getDao()).findAllByStatuses(statuses, numbers);
        Map<String, Order> orderMap = findAllAsMap(orders, page, sort);

        fillOrdersWithTimes(orderMap);
        fillOrdersWithDescriptions(orderMap);

        return orderMap.values().stream()
                .sorted(Order.getComparator(sort))
                .collect(Collectors.toList());
    }

    private String getStatuses(Set<StatusName> orderToShow) {
        StringBuilder sb = new StringBuilder(" and status in ('");
        String body = orderToShow.stream()
                .map(Enum::toString)
                .collect(Collectors.joining("', '"));

//        orderToShow.forEach(statusName -> sb.append(" and status = '").append(statusName.toString()).append("' "));
        return sb.append(body).append("') ").toString();
    }

    private void fillOrdersWithDescriptions(Map<String, Order> orderMap) {
        Map<String, String> designerPseudoName = descriptionService.getDesignerPseudoNames();
        for (Description d : descriptionService.findAll()) {
            if (designerPseudoName.containsKey(d.getDesigner())) {
                d.setDesigner(designerPseudoName.get(d.getDesigner()));
            }
            String key = d.getIdOrder();
            if (orderMap.containsKey(key)) {
                orderMap.get(key).getDescriptions().add(d);
            }
        }
    }
}