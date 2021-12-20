package org.aleks4ay.developer.dao.mapper;

import org.aleks4ay.developer.model.OrderTime;
import org.aleks4ay.developer.model.StatusName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderTimeMapper implements ObjectMapperWithCreate<OrderTime> {

    @Override
    public OrderTime extractFromResultSet(ResultSet rs) throws SQLException {

        return new OrderTime(
                rs.getLong("id"),
                rs.getString("id_order"),
                StatusName.valueOf(rs.getString("status")),
                rs.getTimestamp("time").toLocalDateTime()
        );
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, OrderTime time) throws SQLException {
        statement.setString(1, time.getIdOrder());
        statement.setString(2, time.getStatusName().toString());
        statement.setTimestamp(3, Timestamp.valueOf(time.getTime()));
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Object key) throws SQLException {
        statement.setLong(1, (long) key);
    }
}