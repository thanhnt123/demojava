package com.demoproject.framework.gearman;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.demoproject.framework.util.ConfigUtil;
import com.demoproject.framework.util.ConvertUtil;
import com.demoproject.framework.util.xxtea.XXTEA;

public class GWorkerManager {

    private static final Logger logger;
    private static final int sleepIdle = 1000;
    private final List<WorkerRunner> lstWorker;
    private String CONFIG_KEY = "demo_2025ss";

    public GWorkerManager() {
        this.lstWorker = new ArrayList<>();
    }

    public void start(final String[] args) {
        if (args.length == 0) {
            return;
        }
        for (final String serviceName : args) {
            this.CONFIG_KEY = ConvertUtil.toString(ConfigUtil.getString(serviceName, "configkey"), "demo_2025ss");
            final String host = XXTEA.decryptBase64StringToString(ConvertUtil.toString(ConfigUtil.getString(serviceName, "host")), this.CONFIG_KEY);
            final int port = ConvertUtil.toInt(XXTEA.decryptBase64StringToString(ConfigUtil.getString(serviceName, "port"), this.CONFIG_KEY));
            final int workerNumber = ConvertUtil.toInt(ConfigUtil.getInteger(serviceName, "worker"));
            final String[] functions = ConfigUtil.getStringArray(serviceName, "function");
            final Map<String, String> mapFuncs = new HashMap<String, String>();
            try {
                for (final String item : functions) {
                    final int kvp = item.indexOf("=");
                    String functionName;
                    String className;
                    if (kvp > 0) {
                        functionName = item.substring(0, kvp).trim();
                        className = item.substring(kvp + 1).trim();
                    } else {
                        functionName = item.trim();
                        className = item.trim();
                    }
                    mapFuncs.put(functionName, className);
                }
                for (int w = 0; w < workerNumber; ++w) {
                    final WorkerRunner worker = new WorkerRunner(host, port, mapFuncs);
                    this.lstWorker.add(worker);
                    new Thread(worker).start();
                }
            } catch (Exception ex) {
                GWorkerManager.logger.error((Object) ("having exception when start service " + serviceName + " - " + ex.toString()));
            }
        }
    }

    public boolean stop() {
        for (final WorkerRunner worker : this.lstWorker) {
            worker.stop();
        }
        boolean isRunning = true;
        while (isRunning) {
            isRunning = false;
            for (final WorkerRunner worker2 : this.lstWorker) {
                if (worker2.isRunning()) {
                    isRunning = true;
                    break;
                }
            }
            this.sleep();
        }
        return true;
    }

    private void sleep() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ex) {
            GWorkerManager.logger.error((Object) ex.toString());
        }
    }

    public boolean status() {
        for (final WorkerRunner worker : this.lstWorker) {
            if (worker.isRunning()) {
                return true;
            }
        }
        return false;
    }

    static {
        logger = Logger.getLogger((Class) GWorkerManager.class);
    }
}
