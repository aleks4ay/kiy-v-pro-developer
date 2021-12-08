package org.aleks4ay.developer.dao;

import java.sql.Connection;

public interface ConnectionBase {

    Connection getConnection();
    void closeConnection(Connection conn);
}