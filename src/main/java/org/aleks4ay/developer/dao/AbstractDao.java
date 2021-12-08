package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.ObjectMapper;
import org.aleks4ay.developer.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

abstract class AbstractDao<T extends BaseEntity<T>> {

    private static final Logger log = LoggerFactory.getLogger(AbstractDao.class);
    public abstract String getEntityName();
    private final ConnectionBase connectionBase;

    ObjectMapper<T> objectMapper;

    AbstractDao(ObjectMapper<T> objectMapper, ConnectionBase connectionBase) {
        this.objectMapper = objectMapper;
        this.connectionBase = connectionBase;
    }

    Connection getConnection() {
        return connectionBase.getConnection();
    }

    void closeConnection(Connection connection) {
        connectionBase.closeConnection(connection);
    }

    Optional<T> findAbstractById(String sql, String id) throws SQLException {
        Connection connection = getConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)){
            prepStatement.setString(1, id);
            ResultSet rs = prepStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(objectMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            log.warn("Exception during reading '{}'. Sql: '{}'.", getEntityName(), sql, e);
            throw e;
        } finally {
            closeConnection(connection);
        }
        return Optional.empty();
    }

    List<T> findAbstractAll(String sql) {
        Connection connection = getConnection();
        try (Statement st = connection.createStatement()){
            List<T> entities = new ArrayList<>();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                T t = objectMapper.extractFromResultSet(rs);
                entities.add(t);
            }
            return entities;

        } catch (SQLException e) {
            log.warn("Exception during reading '{}'. Sql: '{}'.", getEntityName(), sql, e);
            return Collections.emptyList();
        } finally {
            closeConnection(connection);
        }
    }

    List<T> findAbstractAllById(String sql, String id) {
        Connection connection = getConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)){
            List<T> entities = new ArrayList<>();
            prepStatement.setString(1, id);
            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                T t = objectMapper.extractFromResultSet(rs);
                entities.add(t);
            }
            return entities;

        } catch (SQLException e) {
            log.warn("Exception during reading '{}'. Sql: '{}'.", getEntityName(), sql, e);
            return Collections.emptyList();
        } finally {
            closeConnection(connection);
        }
    }

    boolean updateAbstract(String sql, T t) {
        Connection connection = getConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)){
            objectMapper.insertToResultSet(prepStatement, t);
            boolean result =  prepStatement.execute();
            if (result) {
                log.info("Was updated {}. New value: {}", getEntityName(), t.toString());
            } else {
                log.info("{} with is '{}' was not updated. Entity: {}", getEntityName(), t.getId(), t.toString());
            }
            return result;
        } catch (SQLException e) {
            log.warn("Exception during updating '{}'. Sql: '{}'.", getEntityName(), sql, e);
        } finally {
            closeConnection(connection);
        }
        return false;
    }
}