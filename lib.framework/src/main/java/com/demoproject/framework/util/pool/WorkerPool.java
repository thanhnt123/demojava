package com.demoproject.framework.util.pool;

public class WorkerPool extends AbstractPool {

    private static WorkerPool sInstance;

    public WorkerPool() {
        super("worker");
    }

    public static synchronized WorkerPool getInstance() {
        if (sInstance == null) {
            sInstance = new WorkerPool();
        }
        return sInstance;
    }
}
