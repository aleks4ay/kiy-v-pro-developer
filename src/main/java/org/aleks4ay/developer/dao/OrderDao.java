package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.OrderMapper;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.util.*;

public class OrderDao extends AbstractDao<Order> implements BaseDao<Order> {

    public OrderDao(ConnectionPool connectionPool) {
        super(new OrderMapper(), connectionPool);
    }


    public List<Order> findAll(String sql) {
        return findAbstractAll(sql);
    }

    @Override
    public String getEntityName() {
        return "Order";
    }

    public void updateStatusName(String id, String statusName) {
        updateStringAbstract(ConstantsSql.ORDER_UPDATE_STATUS, id, statusName);
    }
}