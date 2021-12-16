package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.DescriptionTimeMapper;
import org.aleks4ay.developer.model.DescriptionTime;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.util.List;

public class DescriptionTimeDao extends AbstractDao<DescriptionTime> implements BaseDao<DescriptionTime> {

    public DescriptionTimeDao(ConnectionBase connectionBase) {
        super(new DescriptionTimeMapper(), connectionBase);
    }

    public boolean create(DescriptionTime time) {
        return updateAbstract(ConstantsSql.DESCRIPTION_TIME_CREATE, time);
    }


    @Override
    public List<DescriptionTime> findAll() {
        return findAbstractAll(ConstantsSql.DESCRIPTION_TIME_FIND_ALL);
    }

    public List<DescriptionTime> findAllByIdOrder(String id) {
        return findAbstractAllById(ConstantsSql.DESCRIPTION_TIME_FIND_ALL_BY_ORDER_ID, id + "-%");
    }

    @Override
    public String getEntityName() {
        return "DescriptionTime";
    }
}
