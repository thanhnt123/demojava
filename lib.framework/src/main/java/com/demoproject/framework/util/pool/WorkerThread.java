package com.demoproject.framework.util.pool;

public class WorkerThread extends MonitoredThread {

    AbstractPool mPool;
    private static int sID = 0;
    public boolean waitingForDoJob;
    public boolean activeWorker;

    public WorkerThread(AbstractPool aPool) {
        super(aPool.getPoolName() + " Worker #" + WorkerThread.sNextID());
        this.mPool = aPool;
    }

    public static synchronized int sNextID() {
        return ++sID;
    }

    public boolean isActiveWorker() {
        return this.activeWorker;
    }

    @Override
    public void run() {
        System.out.println("started");
        this.activeWorker = true;
        while (!WorkerThread.interrupted()) {
            this.setWaitMode(true);
            AbstractJob aJob = this.mPool.getNextJob();
            if (aJob == null) {
                break;
            }
            this.setWaitMode(false);
            this.waitingForDoJob = false;
            aJob.doJob();
            this.waitingForDoJob = false;
            //System.out.println("Finished Job");
            ConfigPool.clearTrans();
        }
        this.mPool.removeWorker(this.getName());
        System.out.println("stopped");
    }
}
