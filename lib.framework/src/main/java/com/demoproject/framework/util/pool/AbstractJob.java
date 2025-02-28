package com.demoproject.framework.util.pool;

public abstract class AbstractJob {

    public abstract void doJob();

    public String getJobType() {
        return "default";
    }
}
