package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.dao.OrderDao;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.model.SortWay;
import org.aleks4ay.developer.model.StatusName;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService extends AbstractService<Order> {

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
}