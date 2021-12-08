package org.aleks4ay.developer.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.aleks4ay.developer.tools.ProtectedConfigFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyVetoException;
import java.io.File;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectionPool implements ConnectionBase{
    private static final Logger log = LoggerFactory.getLogger(ConnectionPool.class);

    private static final ConnectionPool instance = new ConnectionPool();

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    private static final ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
    private static final String DRIVER_NAME;
    private static final String USER_NAME;
    private static final String PASSWORD;
    static final String URL;
    static final String KEY;

    static {
        final ResourceBundle config = ResourceBundle
                .getBundle("persistence", Locale.ENGLISH);
        DRIVER_NAME = config.getString("database.driver");
        URL = config.getString("database.url");
        USER_NAME = config.getString("database.username");
        KEY = config.getString("database.key");
        PASSWORD = config.getString("database.password");
        initDataSource();
    }

    static void initDataSource() {
        try {
            pooledDataSource.setDriverClass(DRIVER_NAME);
        } catch (PropertyVetoException e) {
            log.warn("Can't set Combo Pooled Data Source from 'persistence.properties'.", e);
        }
        String decriptedPassword = null;
        try {
            decriptedPassword = ProtectedConfigFile.decrypt(PASSWORD, KEY);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        pooledDataSource.setJdbcUrl(URL);
        pooledDataSource.setUser(USER_NAME);
        pooledDataSource.setPassword(decriptedPassword);
        pooledDataSource.setMinPoolSize(5);
        pooledDataSource.setAcquireIncrement(5);
        pooledDataSource.setMaxPoolSize(5);
    }

    @Override
    public Connection getConnection() {
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
                log.debug("SQLException during close connection from {}. {}", ConnectionPool.class, e);
            }
        }
    }
}