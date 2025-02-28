package com.demoproject.framework.gearman;

import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import org.gearman.client.GearmanJobResult;
import org.gearman.client.GearmanJobImpl;
import org.gearman.client.GearmanJob;
import org.apache.commons.pool.impl.GenericObjectPoolFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.Map;
import org.gearman.client.GearmanClient;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import com.demoproject.framework.util.EncryptUtil;
import com.demoproject.framework.util.JsonUtil;
import com.demoproject.framework.util.StringUtil;
import com.demoproject.framework.util.UuidUtil;

public class GClientManager {

    private static final Logger logger;
    private final GenericObjectPool<GearmanClient> pool;
    private static final Map<String, GClientManager> instances;
    private static final Lock createLock;
    private final AtomicLong totalQueueErrors;
    private final String function;

    public GClientManager(final String section) {
        this.totalQueueErrors = new AtomicLong();
        final GConfig config = new GConfig(section);
        this.pool = this.createObjectPool(config);
        this.function = config.function;
    }

    private GenericObjectPool<GearmanClient> createObjectPool(final GConfig config) {
        final GenericObjectPool.Config configPool = new GenericObjectPool.Config();
        configPool.maxActive = config.maxActive;
        configPool.maxIdle = config.maxIdle;
        configPool.minIdle = config.minIdle;
        configPool.maxWait = config.maxWait;
        configPool.minEvictableIdleTimeMillis = config.setMinEvictableIdleTimeMillis;
        configPool.timeBetweenEvictionRunsMillis = config.setTimeBetweenEvictionRunsMillis;
        configPool.testOnBorrow = true;
        configPool.testWhileIdle = true;
        final GClientPool objectPool = new GClientPool(config.host, config.port);
        final GenericObjectPoolFactory<GearmanClient> factory = new GenericObjectPoolFactory(objectPool, configPool);
        final GenericObjectPool<GearmanClient> genericObjectPool = (GenericObjectPool<GearmanClient>) factory.createPool();
        return genericObjectPool;
    }

    public static GClientManager getInstance(String section) {
        try {
            if (!GClientManager.instances.containsKey(section)) {
                GClientManager.createLock.lock();
                try {
                    if (!GClientManager.instances.containsKey(section)) {
                        GClientManager.instances.put(section, new GClientManager(section));
                    }
                } finally {
                    GClientManager.createLock.unlock();
                }
            }
        } catch (Exception ex) {
            System.out.println("getInstance:" + ex.getMessage());
        }
        return GClientManager.instances.get(section);
    }

    public static GClientManager getInstance() {
        final String specific = "gearman";
        return getInstance(specific);
    }

    public GearmanClient borrow() {
        GearmanClient client = null;
        try {
            client = this.pool.borrowObject();
        } catch (Exception ex) {
            GClientManager.logger.error((Object) "borrow error", (Throwable) ex);
        }
        return client;
    }

    public void giveBack(final GearmanClient client) {
        try {
            this.pool.returnObject(client);
        } catch (Exception ex) {
            GClientManager.logger.error((Object) "giveBack error", (Throwable) ex);
        }
    }

    public void invalidate(final GearmanClient client) {
        try {
            this.pool.invalidateObject(client);
        } catch (Exception ex) {
            GClientManager.logger.error((Object) "invalidate error", (Throwable) ex);
        }
    }

    public boolean push(final JobEnt job) {
        return this.push(job, GearmanJob.JobPriority.NORMAL);
    }

    public boolean push(final JobEnt job, final GearmanJob.JobPriority priority) {
        final String jsonData = JsonUtil.serialize((Object) job);
        final byte[] data = EncryptUtil.encodeUTF8(jsonData);
        return this.push(job.uuid, data, this.function, priority);
    }

    public boolean push(final JobEnt job, final String function) {
        return this.push(job, function, GearmanJob.JobPriority.NORMAL);
    }

    public boolean push(JobEnt job, String function, GearmanJob.JobPriority priority) {
        try {
            if (job == null) {
                return false;
            }
            final String jsonData = JsonUtil.serialize(job);
            final byte[] data = EncryptUtil.encodeUTF8(jsonData);
            return this.push(job.uuid, data, function, priority);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean push(final String id, final byte[] data) {
        return this.push(id, data, this.function);
    }

    public boolean push(final String id, final byte[] data, final GearmanJob.JobPriority priority) {
        return this.push(id, data, this.function, priority);
    }

    public boolean push(final String id, final byte[] data, final String function) {
        return this.push(id, data, function, GearmanJob.JobPriority.NORMAL);
    }

    public boolean push(String id, final byte[] data, final String function, final GearmanJob.JobPriority priority) {
        if (StringUtil.isNullOrEmpty(function)) {
            throw new RuntimeException("function cannot be null or empty");
        }
        boolean result = false;
        if (StringUtil.isNullOrEmpty(id)) {
            id = UuidUtil.makeString();
        }
        final GearmanClient client = this.borrow();
        try {
            final GearmanJob job = GearmanJobImpl.createBackgroundJob(function, data, priority, id);
            client.submit(job);
            final GearmanJobResult res = job.get();
            result = res.jobSucceeded();
        } catch (IllegalArgumentException | InterruptedException ex2) {
        } catch (ExecutionException ex) {
            this.totalQueueErrors.incrementAndGet();
            GClientManager.logger.error((Object) ex);
        } finally {
            this.giveBack(client);
        }
        return result;
    }

    public long getTotalErrors() {
        return this.totalQueueErrors.get();
    }

    static {
        logger = Logger.getLogger((Class) GClientManager.class);
        instances = new HashMap<>();
        createLock = new ReentrantLock();
    }
}
