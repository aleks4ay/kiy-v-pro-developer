package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.ClientMapper;
import org.aleks4ay.developer.model.Client;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientDao extends AbstractDao<Client> implements BaseDao<Client> {

    public ClientDao(ConnectionPool connectionPool) {
        super(new ClientMapper(), connectionPool);
    }

    @Override
    public Optional<Client> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.CLIENT_GET_ONE, id);
    }

    @Override
    public List<Client> findAll() {
        return findAbstractAll(ConstantsSql.CLIENT_GET_ALL);
    }

    @Override
    public boolean update(Client client) {
        return updateAbstract(ConstantsSql.CLIENT_UPDATE, client);
    }

    @Override
    public String getEntityName() {
        return "Client";
    }
}