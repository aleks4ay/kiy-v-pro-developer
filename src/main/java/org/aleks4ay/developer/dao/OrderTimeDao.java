package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.OrderTimeMapper;
import org.aleks4ay.developer.model.OrderTime;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.util.List;

public class OrderTimeDao extends AbstractDao<OrderTime> implements BaseDao<OrderTime> {

    public OrderTimeDao(ConnectionBase connectionBase) {
        super(new OrderTimeMapper(), connectionBase);
    }

    public boolean create(OrderTime time) {
        return updateAbstract(ConstantsSql.ORDER_TIME_CREATE, time);
    }


    public List<OrderTime> findAll() {
        return findAbstractAll(ConstantsSql.ORDER_TIME_FIND_ALL);
    }

    @Override
    public String getEntityName() {
        return "DescriptionTime";
    }
}
