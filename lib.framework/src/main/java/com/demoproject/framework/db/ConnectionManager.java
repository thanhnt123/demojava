package com.demoproject.framework.db;

import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPoolFactory;
import java.util.concurrent.locks.Lock;
import java.util.Map;
import java.sql.Connection;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

public class ConnectionManager implements ConnectionIF {

    private static final Logger logger;
    private final GenericObjectPool<Connection> pool;
    private static final Map<String, ConnectionIF> instances;
    private static final Lock createLock;

    public ConnectionManager(final String section) {
        final ConnectionConfig config = new ConnectionConfig(section);
        this.pool = this.createObjectPool(config);
    }

    private GenericObjectPool<Connection> createObjectPool(final ConnectionConfig config) {
        final GenericObjectPool.Config configPool = new GenericObjectPool.Config();
        configPool.maxActive = config.maxActive;
        configPool.maxIdle = config.maxIdle;
        configPool.minIdle = config.minIdle;
        configPool.maxWait = config.maxWait;
        configPool.minEvictableIdleTimeMillis = config.setMinEvictableIdleTimeMillis;
        configPool.timeBetweenEvictionRunsMillis = config.setTimeBetweenEvictionRunsMillis;
        configPool.testOnBorrow = true;
        configPool.testWhileIdle = true;
        final ConnectionPool connectionObjectPool = new ConnectionPool(config.driver, config.url, config.user, config.password);
        final GenericObjectPoolFactory<Connection> factory = (GenericObjectPoolFactory<Connection>) new GenericObjectPoolFactory((PoolableObjectFactory) connectionObjectPool, configPool);
        final GenericObjectPool<Connection> genericObjectPool = (GenericObjectPool<Connection>) factory.createPool();
        return genericObjectPool;
    }

    public static ConnectionIF getInstance(final String section) {
        if (!ConnectionManager.instances.containsKey(section)) {
            ConnectionManager.createLock.lock();
            try {
                if (!ConnectionManager.instances.containsKey(section)) {
                    ConnectionManager.instances.put(section, new ConnectionManager(section));
                }
            } finally {
                ConnectionManager.createLock.unlock();
            }
        }
        return ConnectionManager.instances.get(section);
    }

    public static ConnectionIF getInstance() {
        final String specific = "db_default";
        return getInstance(specific);
    }

    @Override
    public Connection borrow() {
        Connection client = null;
        try {
            client = (Connection) this.pool.borrowObject();
        } catch (Exception ex) {
            ConnectionManager.logger.error((Object) "borrow error", (Throwable) ex);
        }
        return client;
    }

    @Override
    public void giveBack(final Connection conn) {
        try {
            this.pool.returnObject(conn);
        } catch (Exception ex) {
            ConnectionManager.logger.error((Object) "giveBack error", (Throwable) ex);
        }
    }

    @Override
    public void invalidate(final Connection conn) {
        try {
            this.pool.invalidateObject(conn);
        } catch (Exception ex) {
            ConnectionManager.logger.error((Object) "invalidate error", (Throwable) ex);
        }
    }

    static {
        logger = Logger.getLogger((Class) ConnectionManager.class);
        instances = new HashMap<>();
        createLock = new ReentrantLock();
    }

}
