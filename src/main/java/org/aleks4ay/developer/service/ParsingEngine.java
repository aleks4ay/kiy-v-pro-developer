package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.DescriptionDao;
import org.aleks4ay.developer.dao.OrderDao;
import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.model.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParsingEngine {

    ConnectionPool connectionPool = ConnectionPool.getInstance();
    OrderService orderService = new OrderService(new OrderDao(connectionPool));
    DescriptionService descriptionService = new DescriptionService(new DescriptionDao(connectionPool));


    public List<Order> getOrdersWithDescriptions(Page page) {
        Map<String, Order> orderMap = findAllAsMap(page);
        for (Description d : descriptionService.findAll()) {
            String key = d.getIdOrder();
            if (orderMap.containsKey(key)) {
                orderMap.get(key).getDescriptions().add(d);
            }
        }
        return new ArrayList<>(orderMap.values());
    }

    public Map<String, Order> findAllAsMap(Page page) {
        List<Order> orders = orderService.findAllFilled();
        orders = orders.stream()
                .limit(45)
                .collect(Collectors.toList());
//        page.setMaxPosition(orders.size() / page.getPositionOnPage());
        page.setSize(orders.size());
        if (page.getPosition() > page.getMaxPosition()) {
            return Collections.emptyMap();
        }
        return orders.stream()
                .skip(page.getPosition() * page.getPositionOnPage())
                .limit(page.getPositionOnPage())
                .collect(Collectors.toMap(Order::getId, order -> order));
    }
}
