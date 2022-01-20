package org.aleks4ay.developer.dao_old;

import org.aleks4ay.developer.dao.ConnectionBase;
import org.aleks4ay.developer.model.StatusName;
import org.aleks4ay.developer.model.TypeName;
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
                OldDboObject newOldDboObject = extractFromResultSet(rs);
                if (newOldDboObject != null) {
                    entities.add(newOldDboObject);
                }
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
        for (int i = 2; i <= 7; i++) {
            long oldTime = rs.getLong("time_" + i);
            if (oldTime != 0L) {
                dbo.addTime(i, new Timestamp(oldTime).toLocalDateTime());
            }
        }
        if (dbo.getStatusName() == StatusName.FACTORY && rs.getLong("time_7") == 0L && rs.getLong("time_2") != 0L) {
            dbo.addTime(7, new Timestamp(rs.getLong("time_2")).toLocalDateTime());
        }
        if (dbo.getTypeName() == TypeName.FACTORY && rs.getLong("time_7") == 0L && rs.getLong("time_2") != 0L) {
            dbo.addTime(7, new Timestamp(rs.getLong("time_2")).toLocalDateTime());
        }
        if (dbo.getTypeName() == TypeName.OTHER && rs.getLong("time_24") != 0L) {
            dbo.addTime(24, new Timestamp(rs.getLong("time_24")).toLocalDateTime());
        }
        return dbo.getTimes().size() > 0 ? dbo : null;
    }
}