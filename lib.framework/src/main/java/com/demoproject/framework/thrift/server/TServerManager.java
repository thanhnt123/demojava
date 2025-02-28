package com.demoproject.framework.thrift.server;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class TServerManager {

    private final Logger logger;
    private final List<TServerObject> servers;
    private TServerObject server;

    public TServerManager() {
        this.logger = Logger.getLogger((Class) TServerManager.class);
        this.servers = new ArrayList<>();
    }

    public void add(final TServerBuilder tserver) {
        final TServerObject object = tserver.getServer();
        if (object == null) {
            return;
        }
        this.servers.add(object);
    }

    public void set(final TServerBuilder tserver) {
        this.server = tserver.getServer();
    }

    public void start() {
        if (this.server != null) {
            if (this.servers.size() > 0) {
                this.servers.clear();
            }
            this.servers.add(this.server);
        }
        for (int i = 0; i < this.servers.size(); ++i) {
            final TServerObject object = this.servers.get(i);
            if (object != null) {
                this.logger.info((Object) ("Started thrift server is running on " + object.host + ":" + object.port));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            object.server.serve();
                        } catch (Exception ex) {
                            TServerManager.this.logger.error((Object) "thrift server error", (Throwable) ex);
                        }
                    }
                }).start();
            }
        }
        this.servers.clear();
    }
}
