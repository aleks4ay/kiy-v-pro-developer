package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.TmcMapper;
import org.aleks4ay.developer.model.Tmc;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TmcDao extends AbstractDao<Tmc> implements BaseDao<Tmc> {

    public TmcDao(ConnectionBase connectionBase) {
        super(new TmcMapper(), connectionBase);
    }

    @Override
    public Optional<Tmc> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.TMC_GET_ONE, id);
    }

    @Override
    public List<Tmc> findAll() {
        return findAbstractAll(ConstantsSql.TMC_GET_ALL);
    }

    @Override
    public boolean update(Tmc tmc) {
        return updateAbstract(ConstantsSql.TMC_UPDATE, tmc);
    }

    @Override
    public String getEntityName() {
        return "Tmc";
    }
}