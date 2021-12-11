package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.dao.OrderDao;
import org.aleks4ay.developer.model.Order;

import java.util.List;

public class OrderService extends AbstractService<Order> {

    public OrderService(BaseDao<Order> orderDao) {
        super(orderDao);
    }

    public List<Order> findAllFilled() {
        return ((OrderDao)getDao()).findAllFilled();
    }
}