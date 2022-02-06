package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.model.*;
import org.aleks4ay.developer.tools.DateTool;

import java.util.*;
import java.util.stream.Collectors;

import static org.aleks4ay.developer.tools.ConstantsSql.*;

public class OrderService extends AbstractService<Order> {

    private final OrderDao orderDao = (OrderDao)getDao();
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    OrderTimeService orderTimeService = new OrderTimeService(new OrderTimeDao(connectionPool));
    DescriptionService descriptionService = new DescriptionService(new DescriptionDao(connectionPool));

    public OrderService(BaseDao<Order> orderDao) {
        super(orderDao);
    }


    public List<Order> getOrdersWithDescriptionsKb(Page page, SortWay sortWay) {
        String sqlOrder = ORDER_BASE + BY_STATUS_KB;
        String sqlDescription = DESCRIPTION_START + BY_STATUS_KB + DESCRIPTION_END;
        return findAll(page, sqlOrder, sortWay, sqlDescription);
    }

    public List<Order> getOrdersWithDescriptionsParsing(Page page, SortWay sortWay) {
        String sqlOrder = ORDER_BASE + BY_STATUS_NEW;
        String sqlDescription = DESCRIPTION_START + BY_STATUS_NEW + DESCRIPTION_END;
        return findAll(page, sqlOrder, sortWay, sqlDescription);
    }

    public List<Order> getOrdersWithDescriptionsParsingSearch(Page page, SortWay sortWay, String number) {
        String sqlOrder = ORDER_BASE + BY_NUMBER.replace(PARAMETER, number);
        String sqlDescription = DESCRIPTION_START + DESCRIPTION_END;
        return findAll(page, sqlOrder, sortWay, sqlDescription);
    }

    public List<Order> getOrdersWithDescriptionsManager(Page page, SortWay sortWay, Set<StatusName> orderToShow, String numbers) {
        String statuses = getStatuses(orderToShow);
        String sqlOrder = ORDER_BASE + BY_STATUS.replace(PARAMETER, statuses) + BY_NUMBER.replace(PARAMETER, numbers);
        String sqlDescription = DESCRIPTION_START + DESCRIPTION_END;
        return findAll(page, sqlOrder, sortWay, sqlDescription);
    }

    public List<Order> getOrdersWithDescriptionsFind(Page page, SortWay sortWay, String yearText, String numberText) {

        String sqlByYear = DateTool.getCorrectYearAsString(yearText);
        sqlByYear = sqlByYear.equals("") ? "" : BY_YEAR.replace(PARAMETER, sqlByYear);

        String sqlOrder = ORDER_BASE + sqlByYear + BY_NUMBER.replace(PARAMETER, numberText);
        String sqlDescription = DESCRIPTION_START + DESCRIPTION_END;
        return findAll(page, sqlOrder, sortWay, sqlDescription);
    }


    public List<Order> findAll(Page page, String sqlOrder, SortWay sortWay, String sqlDescription) {

        List<Order> orders = orderDao.findAll(sqlOrder + sortWay.getSql());

        Map<String, Order> orderPageMap = findOrderPageAsMap(page, orders);

        fillOrdersWithTimes(orderPageMap);
        fillOrdersWithDescriptions(orderPageMap, sqlDescription);

        return orderPageMap.values().stream()
                .sorted(Order.getComparator(sortWay))
                .collect(Collectors.toList());
    }


    public Map<String, Order> findOrderPageAsMap(Page page, List<Order> orders) {
        page.setSize(orders.size());
        if (page.getPosition() > page.getMaxPosition()) {
            return Collections.emptyMap();
        }
        return orders.stream()
                .skip(page.getPosition() * page.getPositionOnPage())
                .limit(page.getPositionOnPage())
                .collect(Collectors.toMap(Order::getId, order -> order));
    }

    private void fillOrdersWithTimes(Map<String, Order> orderMap) {
        Map<String, List<OrderTime>> orderTimeMap = orderTimeService.findAllAsMap();
        for (Order o : orderMap.values()) {
            o.setTimes(orderTimeMap.get(o.getId()));
        }
    }

    private void fillOrdersWithDescriptions(Map<String, Order> orderMap, String sqlDescription) {
        for (Description d : descriptionService.findAll(sqlDescription)) {
            String key = d.getIdOrder();
            if (orderMap.containsKey(key)) {
                orderMap.get(key).getDescriptions().add(d);
            }
        }
    }


    public void updateStatus(String orderId, StatusName status) {
        orderDao.updateStatusName(orderId, status.toString());
    }

    private String getStatuses(Set<StatusName> orderToShow) {
        StringBuilder sb = new StringBuilder("'");
        String body = orderToShow.stream()
                .map(Enum::toString)
                .collect(Collectors.joining("', '"));

        return sb.append(body).append("'").toString();
    }
}