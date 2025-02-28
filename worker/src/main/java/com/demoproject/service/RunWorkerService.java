package com.demoproject.service;

import com.demoproject.framework.gearman.GWorkerManager;
import com.demoproject.framework.util.LogUtil;
import com.demoproject.framework.util.SystemErrToLog4j;
import com.demoproject.framework.util.SystemOutToLog4j;
import com.demoproject.helper.ConfigInfo;
import org.apache.log4j.Logger;

public class RunWorkerService {

    private static final Logger logger = LogUtil.getLogger(RunWorkerService.class);

    static {
        SystemOutToLog4j.enableForClass(RunWorkerService.class);
        SystemErrToLog4j.enableForClass(RunWorkerService.class);
    }

    public static void main(String[] args) {
        GWorkerManager gm = new GWorkerManager();
        gm.start(new String[]{ConfigInfo.GEARMAN_WORKER});

        logger.info("gearman worker start!");
    }
}
