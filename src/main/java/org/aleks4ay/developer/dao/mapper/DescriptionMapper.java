package org.aleks4ay.developer.dao.mapper;

import org.aleks4ay.developer.model.Description;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DescriptionMapper implements ObjectMapper<Description> {
    @Override
    public Description extractFromResultSet(ResultSet rs) throws SQLException {
        return new Description(
                rs.getString("id"),
                rs.getString("iddoc"),
                rs.getInt("position"),
                rs.getString("id_tmc"),
                rs.getInt("quantity"),
                rs.getString("descr_second"),
                rs.getInt("size_a"),
                rs.getInt("size_b"),
                rs.getInt("size_c"),
                rs.getString("embodiment")
        );
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Description description) throws SQLException {
        statement.setString(10, description.getId());
        statement.setString(1, description.getIdDoc());
        statement.setInt(2, description.getPosition());
        statement.setString(3, description.getIdTmc());
        statement.setInt(4, description.getQuantity());
        statement.setString(5, description.getDescrSecond());
        statement.setInt(6, description.getSizeA());
        statement.setInt(7, description.getSizeB());
        statement.setInt(8, description.getSizeC());
        statement.setString(9, description.getEmbodiment());
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Description description) throws SQLException {
        statement.setString(1, description.getId());
    }
}