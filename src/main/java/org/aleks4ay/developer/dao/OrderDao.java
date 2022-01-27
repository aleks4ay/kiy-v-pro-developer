package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.OrderMapper;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.tools.ConstantsSql;

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

    public List<Order> findAllParsing() {
        return findAbstractAll(ConstantsSql.ORDER_GET_ALL_NEW);
    }

    public List<Order> findAllLike(String yearText, String numberText) {
        String sqlStart = yearText.equals("") ? ConstantsSql.ORDER_GET_ALL_LIKE : ConstantsSql.ORDER_GET_ALL_LIKE + ConstantsSql.AND_YEAR_EQUAL + yearText;
        return findAbstractAll(sqlStart + ConstantsSql.AND_DOCUMENT_NUMBER_LIKE + numberText + ConstantsSql.END_LIKE);
    }
}