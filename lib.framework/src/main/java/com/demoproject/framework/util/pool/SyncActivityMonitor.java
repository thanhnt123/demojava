package com.demoproject.framework.util.pool;

import java.util.Date;

public interface SyncActivityMonitor {

    public Date getLastActivity();

    public boolean getWaitMode();
}
