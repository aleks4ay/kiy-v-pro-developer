package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.model.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService extends AbstractService<Order> {

    public OrderService(BaseDao<Order> orderDao) {
        super(orderDao);
    }

/*    public List<Order> findAllParsing(long position, long positionOnList) {
        return findAll().stream()
                .skip(position * positionOnList)
                .limit(positionOnList)
                .collect(Collectors.toList());
    }*/


}