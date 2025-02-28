package com.demoproject.framework.db;

import java.sql.SQLException;
import java.sql.DriverManager;
import org.apache.log4j.Logger;
import java.sql.Connection;
import org.apache.commons.pool.BasePoolableObjectFactory;

public class ConnectionPool extends BasePoolableObjectFactory<Connection> {

    private static final Logger logger;
    private final String driver;
    private final String url;
    private final String user;
    private final String password;

    public ConnectionPool(final String driver, final String url, final String user, final String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection makeObject() throws Exception {
        Class.forName(this.driver).newInstance();
        return DriverManager.getConnection(this.url, this.user, this.password);
    }

    public void destroyObject(final Connection conn) throws Exception {
        if (conn == null) {
            return;
        }
        conn.close();
    }

    public boolean validateObject(final Connection conn) {
        if (conn == null) {
            return false;
        }
        try {
            boolean result = true;
            result = (result && conn.isValid(1));
            result = (result && !conn.isClosed());
            return result;
        } catch (SQLException ex) {
            ConnectionPool.logger.error((Object) "validateObject error", (Throwable) ex);
            return false;
        }
    }

    static {
        logger = Logger.getLogger((Class) ConnectionPool.class);
    }

}
