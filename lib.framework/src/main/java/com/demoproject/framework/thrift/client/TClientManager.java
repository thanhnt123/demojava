package com.demoproject.framework.thrift.client;

import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import java.util.concurrent.locks.Lock;
import java.util.Map;
import org.apache.log4j.Logger;

public class TClientManager<T> {

    private static final Logger logger;
    private static final Map<String, TClientManager> instances;
    private static final Lock createLock;
    private final GenericObjectPool<TClientObject<T>> pool;

    public TClientManager(final String section, final Class<?> serviceClass) {
        final TClientConfig config = new TClientConfig(section);
        this.pool = this.createObjectPool(config, serviceClass);
    }

    private GenericObjectPool<TClientObject<T>> createObjectPool(final TClientConfig config, final Class<?> serviceClass) {
        final TClientPool<T> clientObjectPool = new TClientPool<T>(config.host, config.port, config.timeout, config.framed, config.protocol, config.ping, serviceClass);
        final GenericObjectPool.Config configPool = new GenericObjectPool.Config();
        configPool.maxActive = config.maxActive;
        configPool.maxIdle = config.maxIdle;
        configPool.minIdle = config.minIdle;
        configPool.maxWait = config.maxWait;
        configPool.minEvictableIdleTimeMillis = config.setMinEvictableIdleTimeMillis;
        configPool.timeBetweenEvictionRunsMillis = config.setTimeBetweenEvictionRunsMillis;
        configPool.testOnBorrow = true;
        configPool.testWhileIdle = true;
        final GenericObjectPoolFactory<TClientObject<T>> factory = (GenericObjectPoolFactory<TClientObject<T>>) new GenericObjectPoolFactory((PoolableObjectFactory) clientObjectPool, configPool);
        final GenericObjectPool<TClientObject<T>> genericObjectPool = (GenericObjectPool<TClientObject<T>>) factory.createPool();
        return genericObjectPool;
    }

    public static TClientManager getInstance(final String section, final Class<?> serviceClass) {
        final String instance = serviceClass.getCanonicalName() + section;
        if (!TClientManager.instances.containsKey(instance)) {
            TClientManager.createLock.lock();
            try {
                if (!TClientManager.instances.containsKey(instance)) {
                    TClientManager.instances.put(instance, new TClientManager(section, serviceClass));
                }
            } finally {
                TClientManager.createLock.unlock();
            }
        }
        return TClientManager.instances.get(instance);
    }

    public TClientObject<T> borrow() {
        TClientObject<T> tco = null;
        try {
            tco = (TClientObject<T>) this.pool.borrowObject();
        } catch (Exception ex) {
            TClientManager.logger.error((Object) "borrow error", (Throwable) ex);
        }
        return tco;
    }

    public void giveBack(final TClientObject<T> tco) {
        try {
            this.pool.returnObject(tco);
        } catch (Exception ex) {
            TClientManager.logger.error((Object) "giveBack error", (Throwable) ex);
        }
    }

    public void invalidate(final TClientObject<T> tco) {
        try {
            this.pool.invalidateObject(tco);
        } catch (Exception ex) {
            TClientManager.logger.error((Object) "invalidate error", (Throwable) ex);
        }
    }

    static {
        logger = Logger.getLogger((Class) TClientManager.class);
        instances = new HashMap<>();
        createLock = new ReentrantLock();
    }
}
