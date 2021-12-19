package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.OrderMapper;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.tools.ConstantsSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class OrderDao extends AbstractDao<Order> implements BaseDao<Order> {

    public OrderDao(ConnectionPool connectionPool) {
        super(new OrderMapper(), connectionPool);
    }

    @Override
    public List<Order> findAll() {
        return findAbstractAll(ConstantsSql.ORDER_GET_ALL_NEW);
    }

    @Override
    public String getEntityName() {
        return "Order";
    }

    public boolean updateStatusName(String id, String statusName) {
        return updateStringAbstract(ConstantsSql.ORDER_UPDATE_STATUS, id, statusName);
    }

    public List<Order> findAllKb(String sort) {
        return findAbstractAll(ConstantsSql.ORDER_GET_ALL_KB + sort);
    }
}