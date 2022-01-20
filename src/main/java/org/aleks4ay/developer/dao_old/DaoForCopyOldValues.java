package org.aleks4ay.developer.dao_old;

import org.aleks4ay.developer.dao.ConnectionBase;
import org.aleks4ay.developer.dao.mapper.DescriptionTimeMapper;
import org.aleks4ay.developer.dao.mapper.ObjectMapperWithCreate;
import org.aleks4ay.developer.dao_old.mapper.OldDboObjectMapper;
import org.aleks4ay.developer.model.BaseEntity;
import org.aleks4ay.developer.model.DescriptionTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DaoForCopyOldValues<T extends BaseEntity<T>> {

    private static final Logger log = LoggerFactory.getLogger(DaoForCopyOldValues.class);
    private final ConnectionBase connectionBase;

    public DaoForCopyOldValues(ConnectionBase connectionBase) {
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
    }


    public void saveAbstractParametersAll(String sql, List<T> times, ObjectMapperWithCreate<T> objectMapper) {
        if (times.isEmpty()) {
            return;
        }
        int result = 0;
        Connection connection = getConnection();
        for (T time : times) {
            try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
                objectMapper.insertToResultSet(prepStatement, time);
                prepStatement.executeUpdate();
                result ++;
                log.debug("Will be created: {}", time);
            } catch (SQLException e) {
                //NOP
            }
        }
        closeConnection(connection);
        log.debug("{} '{}' created.", result, times.get(0).getEntityName());
    }
}
