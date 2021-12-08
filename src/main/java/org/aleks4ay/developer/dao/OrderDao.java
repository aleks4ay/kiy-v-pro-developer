package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.OrderMapper;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.tools.ConstantsSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class OrderDao extends AbstractDao<Order> implements BaseDao<Order> {
    private static final Logger log = LoggerFactory.getLogger(OrderDao.class);

    public OrderDao(ConnectionPool connectionPool) {
        super(new OrderMapper(), connectionPool);
    }

    @Override
    public Optional<Order> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.ORDER_GET_ONE, id);
    }

    @Override
    public List<Order> findAll() {
        return findAbstractAll(ConstantsSql.ORDER_GET_ALL);
    }

    @Override
    public boolean update(Order order) {
        return updateAbstract(ConstantsSql.ORDER_UPDATE, order);
    }

    @Override
    public String getEntityName() {
        return "Order";
    }

    public List<String> findIdAll() {
        Connection connection = getConnection();
        try (Statement st = connection.createStatement()){
            List<String> entities = new ArrayList<>();
            ResultSet rs = st.executeQuery(ConstantsSql.ORDER_GET_ALL_ID);
            while (rs.next()) {
                entities.add(rs.getString("iddoc"));
            }
            return entities;
        } catch (SQLException e) {
            log.warn("Exception during reading 'Order.idDoc'. Sql: '{}'.", ConstantsSql.ORDER_GET_ALL_ID, e);
            return Collections.emptyList();
        } finally {
            closeConnection(connection);
        }
    }
}