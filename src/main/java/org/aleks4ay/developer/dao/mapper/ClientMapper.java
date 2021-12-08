package org.aleks4ay.developer.dao.mapper;

import org.aleks4ay.developer.model.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements ObjectMapper<Client> {
    @Override
    public Client extractFromResultSet(ResultSet rs) throws SQLException {
        return new Client(
                rs.getString("id"),
                rs.getString("name")
                );
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Client client) throws SQLException {
        statement.setString(1, client.getName());
        statement.setString(2, client.getId());
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Client client) throws SQLException {
        statement.setString(1, client.getId());
    }
}