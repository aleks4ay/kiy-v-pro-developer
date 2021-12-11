package org.aleks4ay.developer.dao.mapper;

import org.aleks4ay.developer.model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements ObjectMapper<Order> {
    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        return new Order(
                rs.getString("iddoc"),
                rs.getString("docno"),
                rs.getString("client"),
                rs.getString("manager"),
                rs.getInt("duration"),
                rs.getTimestamp("t_create"),
                rs.getTimestamp("t_factory")
        );
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Order order) throws SQLException {
        statement.setString(6, order.getIdDoc());
/*        statement.setString(1, order.getClient());
        statement.setString(2, order.getManager());
        statement.setInt(3, order.getDurationTime());
        statement.setTimestamp(4, order.getDateToFactory());
        statement.setDouble(5, order.getPrice());*/
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Order order) throws SQLException {
        statement.setString(1, order.getIdDoc());
    }
}