package org.aleks4ay.developer.dao.mapper;

import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.model.StatusName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements ObjectMapper<Order> {
    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        return new Order(
                rs.getString("id"),
                rs.getString("doc_number"),
                rs.getString("client"),
                rs.getString("manager"),
                rs.getInt("duration"),
                rs.getTimestamp("t_create"),
                rs.getTimestamp("t_factory"),
                StatusName.valueOf(rs.getString("status"))
        );
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Object key) throws SQLException {
        statement.setString(1, String.valueOf(key));
    }
}