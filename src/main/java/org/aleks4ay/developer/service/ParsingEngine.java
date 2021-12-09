package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.DescriptionDao;
import org.aleks4ay.developer.dao.OrderDao;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.model.Page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ParsingEngine {

    private List<Order> orders;

    ConnectionPool connectionPool = ConnectionPool.getInstance();
    OrderService orderService = new OrderService(new OrderDao(connectionPool));
    DescriptionService descriptionService = new DescriptionService(new DescriptionDao(connectionPool));


    public List<Order> findAllParsing(Page page) {
        orders = orderService.findAll();
        page.setMaxPosition(orders.size() / page.getPositionOnPage());
        if (page.getPosition() > page.getMaxPosition()) {
            return Collections.emptyList();
        }
        return orders.stream()
                .skip(page.getPosition() * page.getPositionOnPage())
                .limit(page.getPositionOnPage())
                .collect(Collectors.toList());
    }
}
