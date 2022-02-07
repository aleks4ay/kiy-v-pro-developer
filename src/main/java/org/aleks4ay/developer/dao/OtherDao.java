package org.aleks4ay.developer.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class OtherDao {

    private static final Logger log = LoggerFactory.getLogger(OtherDao.class);
    private final ConnectionBase connectionBase;

    public OtherDao(ConnectionBase connectionBase) {
        this.connectionBase = connectionBase;
    }


    public String selectSql(String sql) {
        Connection connection = connectionBase.getConnection();
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            StringBuilder sb = new StringBuilder();

            for (int i = 1; i <= columnsNumber; i++){
                sb.append(rsmd.getColumnLabel(i)).append(":  ");
            }
            sb.append(System.lineSeparator());

            while (rs.next()) {
                String item = "";
                for (int i = 1; i <= columnsNumber; i++){
                    if (rsmd.getColumnType(i) == 12) {
                        item = rs.getString(i);
                    }
                    if (rsmd.getColumnType(i) == 4) {
                        item = String.valueOf(rs.getInt(i));
                    }
                    if (rsmd.getColumnType(i) == -5) {
                        item = String.valueOf(rs.getLong(i));
                    }
                    if (rsmd.getColumnType(i) == 91) {
                        item = String.valueOf(rs.getDate(i));
                    }
                    if (rsmd.getColumnType(i) == 93) {
                        item = String.valueOf(rs.getTimestamp(i));
                    }
                    sb.append(item).append(":  ");
                }
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        } catch (SQLException e) {
            log.warn("Exception during doing Sql: '{}'.", sql, e);
            return "";
        } finally {
            connectionBase.closeConnection(connection);
        }
    }

    public void updateSql(String sql) {
        Connection connection = connectionBase.getConnection();
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            log.warn("Exception during doing Sql: '{}'.", sql, e);
        } finally {
            connectionBase.closeConnection(connection);
        }
    }

}