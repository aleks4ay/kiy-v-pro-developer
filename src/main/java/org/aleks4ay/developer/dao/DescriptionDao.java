package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.DescriptionMapper;
import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DescriptionDao extends AbstractDao<Description> implements BaseDao<Description> {

    public DescriptionDao(ConnectionPool connectionPool) {
        super(new DescriptionMapper(), connectionPool);
    }

    @Override
    public Optional<Description> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.DESCRIPTION_GET_ONE, id);
    }

    @Override
    public List<Description> findAll() {
        return findAbstractAll(ConstantsSql.DESCRIPTION_GET_ALL);
    }

    @Override
    public boolean update(Description description) {
        return updateAbstract(ConstantsSql.DESCRIPTION_UPDATE, description);
    }

    @Override
    public String getEntityName() {
        return "Description";
    }

    public List<Description> findByOrderId(String orderId) {
        return findAbstractAllById(ConstantsSql.DESCRIPTION_GET_BY_ORDER_ID, orderId);
    }
}