package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.model.Order;

public class OrderService extends AbstractService<Order> {

    public OrderService(BaseDao<Order> orderDao) {
        super(orderDao);
    }
}