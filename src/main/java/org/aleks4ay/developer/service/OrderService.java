package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.model.*;
import org.aleks4ay.developer.tools.DateTool;

import java.time.LocalDate;
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

    public List<Order> getOrdersWithDescriptionsManager(Page page, SortWay sortWay, Set<StatusName> statusNameSet,
                   Set<TypeName> typeNameSet, LocalDate dateStart, String numbers, String developerIds, String managerId) {

        String statuses = statusNameSet.isEmpty() ? "" : BY_STATUS.replace(PARAMETER, getStatuses(statusNameSet));
        if (statuses.isEmpty()) {
            return new ArrayList<>();
        }
        String types = typeNameSet.isEmpty() ? "" : BY_TYPE.replace(PARAMETER, getTypes(typeNameSet));
        String number = numbers.isEmpty() ? "" : BY_NUMBER.replace(PARAMETER, numbers);
        String dates = dateStart == null ? "" : BY_DATE_CREATE.replace(PARAMETER, dateStart.toString());
        String developer = developerIds.isEmpty() ? "" : BY_DEVELOPER.replace(PARAMETER, getDevelopers(developerIds));
        String manager = managerId.isEmpty() ? "" : BY_MANAGER.replace(PARAMETER, managerId);
        String sqlOrder = ORDER_BASE + statuses + types + dates + developer + manager + number;
        String sqlDescription = DESCRIPTION_START + statuses + types + developer + DESCRIPTION_END;
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

    private String getStatuses(Set<StatusName> statusNameSet) {
        StringBuilder sb = new StringBuilder("'");
        String body = statusNameSet.stream()
                .map(Enum::toString)
                .collect(Collectors.joining("', '"));

        return sb.append(body).append("'").toString();
    }

    private String getDevelopers(String developerIds) {
        String[] ids = developerIds.replace("m5_", "").split(DELIMITER);
        StringBuilder sb = new StringBuilder("'");
        String body = String.join("', '", ids);

        return sb.append(body).append("'").toString();
    }

    private String getTypes(Set<TypeName> typeNameSet) {
        StringBuilder sb = new StringBuilder("'");
        String body = typeNameSet.stream()
                .map(Enum::toString)
                .collect(Collectors.joining("', '"));

        return sb.append(body).append("'").toString();
    }
}