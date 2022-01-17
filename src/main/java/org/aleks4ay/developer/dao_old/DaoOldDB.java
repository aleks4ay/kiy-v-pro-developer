package org.aleks4ay.developer.dao_old;

import org.aleks4ay.developer.dao.ConnectionBase;
import org.aleks4ay.developer.tools.ConstantsSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DaoOldDB {

    private static final Logger log = LoggerFactory.getLogger(DaoOldDB.class);
    private final ConnectionBase connectionBase;

    public DaoOldDB(ConnectionBase connectionBase) {
        this.connectionBase = connectionBase;
    }

    Connection getConnection() {
        return connectionBase.getConnection();
    }

    void closeConnection(Connection connection) {
        connectionBase.closeConnection(connection);
    }


    public List<OldDboObject> findAll() {
        Connection connection = getConnection();

        try (Statement st = connection.createStatement()){
            List<OldDboObject> entities = new ArrayList<>();
            ResultSet rs = st.executeQuery(ConstantsSql.GET_ALL_FROM_OLD_DB);
            while (rs.next()) {
                entities.add(extractFromResultSet(rs));
            }
            return entities;

        } catch (SQLException e) {
            log.warn("Exception during reading data from old DB. Sql: '{}'.", ConstantsSql.GET_ALL_FROM_OLD_DB, e);
            return Collections.emptyList();
        } finally {
            closeConnection(connection);
        }
    }

    private OldDboObject extractFromResultSet(ResultSet rs) throws SQLException {
        OldDboObject dbo = new OldDboObject(
                rs.getString("kod"),
                rs.getInt("type_index"),
                rs.getInt("status_index"),
                rs.getString("designer")
        );
        for (int i = 0; i <= 24; i++) {
            if (i > 7 && i < 22) {
                continue;
            }
            long oldTime = rs.getLong("time_" + i);
            if (oldTime != 0L) {
                dbo.addTime(i, new Timestamp(oldTime).toLocalDateTime());
            }
        }
        return dbo;
    }
}