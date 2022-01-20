package org.aleks4ay.developer.dao_old;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.aleks4ay.developer.dao.ConnectionBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectionPoolOldData implements ConnectionBase {
    private static final Logger log = LoggerFactory.getLogger(ConnectionPoolOldData.class);

    private static final ConnectionPoolOldData instance = new ConnectionPoolOldData();

    private ConnectionPoolOldData() {
    }

    public static ConnectionPoolOldData getInstance() {
        return instance;
    }

    private static final ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
    private static final String DRIVER_NAME;
    private static final String USER_NAME;
    private static final String PASSWORD;
    static final String URL;

    static {
        final ResourceBundle config = ResourceBundle
                .getBundle("persistenceOld", Locale.ENGLISH);
        DRIVER_NAME = config.getString("database.driver");
        URL = config.getString("database.url");
        USER_NAME = config.getString("database.username");
        PASSWORD = config.getString("database.password");
        initDataSource();
    }

    static void initDataSource() {
//        System.out.println("Start init connection");
        try {
            pooledDataSource.setDriverClass(DRIVER_NAME);
        } catch (PropertyVetoException e) {
            log.warn("Can't set Combo Pooled Data Source from 'persistence.properties'.", e);
        }
        pooledDataSource.setJdbcUrl(URL);
        pooledDataSource.setUser(USER_NAME);
        pooledDataSource.setPassword(PASSWORD);
        pooledDataSource.setMinPoolSize(5);
        pooledDataSource.setAcquireIncrement(5);
        pooledDataSource.setMaxPoolSize(5);
//        System.out.println("End init connection");
    }

    @Override
    public Connection getConnection() {
//        System.out.println("Start getConnection");
        try {
            return pooledDataSource.getConnection();
        } catch (SQLException e) {
            log.error("SQLException during get Connection from resource {}. {}",
                    new File("/database.properties").getAbsolutePath(), e);
        }
        return null;
    }

    @Override
    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.debug("SQLException during close connection from {}. {}", ConnectionPoolOldData.class, e);
            }
        }
    }
}