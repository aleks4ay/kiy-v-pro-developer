package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.TmcMapper;
import org.aleks4ay.developer.model.Tmc;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.util.List;

public class TmcDao extends AbstractDao<Tmc> implements BaseDao<Tmc> {

    public TmcDao(ConnectionBase connectionBase) {
        super(new TmcMapper(), connectionBase);
    }

    @Override
    public List<Tmc> findAll() {
        return findAbstractAll(ConstantsSql.TMC_GET_ALL);
    }

    @Override
    public String getEntityName() {
        return "Tmc";
    }
}