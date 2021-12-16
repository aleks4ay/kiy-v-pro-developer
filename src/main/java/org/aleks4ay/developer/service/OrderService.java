package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.dao.OrderDao;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.model.StatusName;

public class OrderService extends AbstractService<Order> {

    public OrderService(BaseDao<Order> orderDao) {
        super(orderDao);
    }

    public boolean updateStatus(String orderId, StatusName status) {
        return ((OrderDao)getDao()).updateStatusName(orderId, status.toString());
    }
}