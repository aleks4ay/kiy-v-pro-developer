package org.aleks4ay.developer.dao_old;

import org.aleks4ay.developer.dao.ConnectionBase;
import org.aleks4ay.developer.dao.mapper.DescriptionTimeMapper;
import org.aleks4ay.developer.dao_old.mapper.OldDboObjectMapper;
import org.aleks4ay.developer.model.DescriptionTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

public class BaseDao {

    private static final Logger log = LoggerFactory.getLogger(BaseDao.class);
    private final ConnectionBase connectionBase;

    public BaseDao(ConnectionBase connectionBase) {
        this.connectionBase = connectionBase;
    }

    Connection getConnection() {
        return connectionBase.getConnection();
    }

    void closeConnection(Connection connection) {
        connectionBase.closeConnection(connection);
    }

    public void saveOrUpdateAbstractAll(String sql, List<OldDboObject> objects, OldDboObjectMapper objectMapper, String target) {
        if (objects.isEmpty()) {
            return;
        }
        int result = 0;
        Connection connection = getConnection();
        for (Object dboObject : objects) {
            try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
                objectMapper.insertToResultSet(prepStatement, dboObject);
                prepStatement.executeUpdate();
                result ++;
                log.debug("Will be updated: {}", dboObject);
            } catch (SQLException e) {
                log.warn("Exception during updating '{}'. SQL = {}.", dboObject, sql, e);
            }
        }
        closeConnection(connection);

        log.debug("{} 'Description' updated.", result);


//        if (objects.isEmpty()) {
//            return true;
//        }
//        Connection connection = getConnection();
//        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
//            connection.setAutoCommit(false);
//            int result = 0;
//
//            for (Object dboObject : objects) {
//                objectMapper.insertToResultSet(prepStatement, dboObject);
//                prepStatement.addBatch();
//                int[] numberOfUpdates = prepStatement.executeBatch();
//                result += IntStream.of(numberOfUpdates).sum();
//                log.debug("Will be {}: {}", target, dboObject);
//            }
//            connection.commit();
//            log.debug("{} {} {}d.", result, OldDboObject.class.getSimpleName(), target);
//            return true;
//        } catch (SQLException e) {
//            log.warn("Exception during saving/updating {} '{}'. SQL = {}.", objects.size(), OldDboObject.class.getSimpleName(), sql, e);
//            return false;
//        } finally {
//            try {
//                connection.setAutoCommit(true);
//                closeConnection(connection);
//            } catch (SQLException e) {
//                log.warn("Can't close connection.", e);
//            }
//        }
    }

    public void saveAbstractAll(String sql, List<DescriptionTime> times, DescriptionTimeMapper objectMapper) {
        if (times.isEmpty()) {
            return;
        }
        int result = 0;
        Connection connection = getConnection();
        for (DescriptionTime time : times) {
            try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
                objectMapper.insertToResultSet(prepStatement, time);
                prepStatement.executeUpdate();
                result ++;
                log.debug("Will be created: {}", time);
            } catch (SQLException e) {
                log.warn("Exception during saving '{}'. SQL = {}.", time, sql, e);
            }
        }
        closeConnection(connection);

        log.debug("{} 'DescriptionTime' created.", result);
    }
}
