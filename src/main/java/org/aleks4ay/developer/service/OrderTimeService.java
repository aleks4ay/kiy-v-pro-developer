package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.dao.OrderTimeDao;
import org.aleks4ay.developer.model.DescriptionTime;
import org.aleks4ay.developer.model.OrderTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderTimeService extends AbstractService<OrderTime> {

    public OrderTimeService(BaseDao<OrderTime> descriptionDao) {
        super(descriptionDao);
    }

    public List<OrderTime> findAllByOrderId(String orderId) {
        return ((OrderTimeDao)getDao()).findByIdOrder(orderId);
    }

    public boolean create(OrderTime time) {
        return ((OrderTimeDao) getDao()).create(time);
    }

    public Map<String, List<OrderTime>> findAllAsMap() {
        return findAll().stream()
                .collect(Collectors.groupingBy(OrderTime::getIdOrder));
    }
}