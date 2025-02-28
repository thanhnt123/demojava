package com.demoproject.framework.gearman;

import org.gearman.worker.DefaultGearmanFunctionFactory;
import org.gearman.worker.GearmanWorkerImpl;
import org.gearman.worker.GearmanWorker;
import java.util.Map;
import org.gearman.common.GearmanNIOJobServerConnection;
import org.apache.log4j.Logger;

public class WorkerRunner implements Runnable {

    private static final Logger logger;
    private final GearmanNIOJobServerConnection conn;
    private final Map<String, String> mapFunctions;
    private final GearmanWorker worker;

    public WorkerRunner(final String host, final int port, final Map<String, String> funs) {
        this.conn = new GearmanNIOJobServerConnection(host, port);
        this.mapFunctions = funs;
        this.worker = new GearmanWorkerImpl();
    }

    @Override
    public void run() {
        try {
            this.worker.addServer(this.conn);
            for (final Map.Entry entry : this.mapFunctions.entrySet()) {
                final DefaultGearmanFunctionFactory gearFunc = new DefaultGearmanFunctionFactory((String) entry.getKey(), (String) entry.getValue());
                this.worker.registerFunctionFactory(gearFunc);
            }
            this.worker.work();
        } catch (Exception ex) {
            WorkerRunner.logger.error(ex.toString());
        }
    }

    public void stop() {
        this.worker.stop();
    }

    public boolean isRunning() {
        return this.worker.isRunning();
    }

    static {
        logger = Logger.getLogger(WorkerRunner.class);
    }
}
