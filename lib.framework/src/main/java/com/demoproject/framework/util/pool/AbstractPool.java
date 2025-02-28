package com.demoproject.framework.util.pool;

import com.demoproject.framework.util.ConfigUtil;
import com.demoproject.framework.util.ConvertUtil;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractPool {

    private static final int INIT = 0;
    private static final int RUNNING = 1;
    private static final int SHUTTING_DOWN = 2;
    private static final int SHUTDOWN = 3;
    private ConcurrentHashMap mWorkers;
    private String mPoolName;
    private int mShutdown;
    private int mPoolSize;
    private BlockingQueue<AbstractJob> mListJob;

    public AbstractPool(String aName) {
        this.mPoolName = aName;
        this.mWorkers = new ConcurrentHashMap();
        this.mListJob = new LinkedBlockingQueue<AbstractJob>();
        this.configReloaded();
        this.mShutdown = 0;
    }

    public void configReloaded() {
        this.mPoolSize = ConvertUtil.toInt(ConfigUtil.getInteger("config_pool", "pool." + this.getPoolName() + ".size"), 5);
        this.adjustPoolSize();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void start() {
        AbstractPool abstractPool = this;
        synchronized (abstractPool) {
            if (this.mShutdown != 0) {
                return;
            }
            this.mShutdown = 1;
        }
        this.adjustPoolSize();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void shutdown() {
        AbstractPool abstractPool = this;
        synchronized (abstractPool) {
            if (this.mShutdown != 0 && this.mShutdown != 1) {
                return;
            }
            this.mShutdown = 2;
            this.notifyAll();
        }
        System.out.println("Shutting down pool " + this.getPoolName());
        abstractPool = this;
        synchronized (abstractPool) {
            if (this.mWorkers.isEmpty()) {
                System.out.println("No workers running, emptying job queue");
                BlockingQueue<AbstractJob> blockingQueue = this.mListJob;
                synchronized (blockingQueue) {
                    this.mListJob.clear();
                }
            }
        }
        this.testShutdownComplete();
    }

    public void removeWorker(String workerName) {
        this.mWorkers.remove(workerName);
        System.out.println("removed " + workerName + " from " + this.mPoolName);
    }

    public void addWorker(String workerName, Object objectWorker) {
        this.mWorkers.putIfAbsent(workerName, objectWorker);
        System.out.println("added " + workerName + " to " + this.mPoolName + " pool");
    }

    private WorkerThread createWorkerThread() {
        return new WorkerThread(this);
    }

    public String getPoolName() {
        return this.mPoolName;
    }

    public int getConfiguredPoolSize() {
        return this.mPoolSize;
    }

    protected void adjustPoolSize() {
        this.adjustPoolSize(this.getConfiguredPoolSize());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void adjustPoolSize(int newCount) {
        Object object;
        int currentCount = 0;
        boolean resized;
        AbstractPool abstractPool = this;
        synchronized (abstractPool) {
        }
        boolean bl = resized = currentCount != newCount;
        if (this.mShutdown == 3 || this.mShutdown == 0) {
            return;
        }
        System.out.println("adjust pool size from " + currentCount + " to " + newCount);
        for (currentCount = this.mWorkers.size(); currentCount < newCount; ++currentCount) {
            WorkerThread wt = this.createWorkerThread();
            wt.activeWorker = true;
            AbstractPool abstractPool2 = this;
            synchronized (abstractPool2) {
                this.mWorkers.putIfAbsent(wt.getName(), wt);
            }
            wt.start();
        }
        while (currentCount > newCount) {
            object = this.mWorkers;
            synchronized (object) {
                WorkerThread wt = (WorkerThread) this.mWorkers.get(currentCount - 1);
                if (!wt.waitingForDoJob) {
                    wt.activeWorker = false;
                    --currentCount;
                    wt.interrupt();
                }
            }
        }
        if (resized) {
            object = this;
            synchronized (object) {
                this.notifyAll();
            }
        }
    }

    public void deployJob(AbstractJob aJob) {
        if (this.mShutdown == 0) {
            this.start();
        }
        if (this.mShutdown != 1) {
            System.out.println(this.getPoolName() + " pool has shutdown");
            return;
        }
        try {
            //System.out.println("deploying job");
            this.mListJob.put(aJob);
        } catch (InterruptedException e) {
            System.out.println("deploying job fail" + e);
        }
    }

    public AbstractJob getNextJob() {
        AbstractJob aJob = null;
        Thread t = Thread.currentThread();
        if (!(t instanceof WorkerThread)) {
            return null;
        }
        WorkerThread wt = (WorkerThread) t;
        if (wt.activeWorker) {
            try {
                aJob = this.mListJob.take();
            } catch (Exception e) {
                System.out.println("getNextJob fail" + e);
            }
        }
        if (aJob == null) {
            if (!wt.waitingForDoJob) {
                wt.activeWorker = false;
                wt.interrupt();
            }
            this.testShutdownComplete();
            return null;
        }
        if (aJob instanceof TransactionJob) {
            ConfigPool.setTrans(((TransactionJob) aJob).getTransID());
        } else {
            ConfigPool.clearTrans();
        }
        ConfigPool.setType(aJob.getJobType());
        return aJob;
    }

    private int jobCount() {
        return this.mListJob.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void testShutdownComplete() {
        if (this.mShutdown != 2) {
            return;
        }
        AbstractPool abstractPool = this;
        synchronized (abstractPool) {
            if (this.jobCount() > 0) {
                return;
            }
        }
        abstractPool = this;
        synchronized (abstractPool) {
            this.mShutdown = 3;
            this.notifyAll();
        }
        System.out.println(this.getPoolName() + " Pool shutdown complete");
    }
}
