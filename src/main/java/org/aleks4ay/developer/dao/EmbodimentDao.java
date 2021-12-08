package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.EmbodimentMapper;
import org.aleks4ay.developer.model.Embodiment;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmbodimentDao extends AbstractDao<Embodiment> implements BaseDao<Embodiment> {

    public EmbodimentDao(ConnectionPool connectionPool) {
        super(new EmbodimentMapper(), connectionPool);
    }

    @Override
    public Optional<Embodiment> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.EMBODIMENT_GET_ONE, id);
    }

    @Override
    public List<Embodiment> findAll() {
        return findAbstractAll(ConstantsSql.EMBODIMENT_GET_ALL);
    }

    @Override
    public boolean update(Embodiment embodiment) {
        return updateAbstract(ConstantsSql.EMBODIMENT_UPDATE, embodiment);
    }

    @Override
    public String getEntityName() {
        return "Embodiment";
    }
}