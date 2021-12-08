package org.aleks4ay.developer.dao.mapper;

import org.aleks4ay.developer.model.Embodiment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmbodimentMapper implements ObjectMapper<Embodiment> {
    @Override
    public Embodiment extractFromResultSet(ResultSet rs) throws SQLException {
        return new Embodiment(
                rs.getString("id"),
                rs.getString("description")
                );
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Embodiment embodiment) throws SQLException {
        statement.setString(1, embodiment.getDescription());
        statement.setString(2, embodiment.getId());
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Embodiment embodiment) throws SQLException {
        statement.setString(1, embodiment.getId());
    }
}