package com.demoproject.framework.gearman;

import org.gearman.common.GearmanJobServerConnection;
import org.gearman.common.GearmanNIOJobServerConnection;
import org.gearman.client.GearmanClientImpl;
import org.gearman.client.GearmanClient;
import org.apache.commons.pool.BasePoolableObjectFactory;

public class GClientPool extends BasePoolableObjectFactory<GearmanClient>
{
    private final String host;
    private final int port;
    
    public GClientPool(final String host, final int port) {
        this.host = host;
        this.port = port;
    }
    
    public GearmanClient makeObject() throws Exception {
        final GearmanClient client = (GearmanClient)new GearmanClientImpl();
        final GearmanJobServerConnection connection = (GearmanJobServerConnection)new GearmanNIOJobServerConnection(this.host, this.port);
        client.addJobServer(connection);
        return client;
    }
    
    public void destroyObject(final GearmanClient client) throws Exception {
        if (client != null) {
            client.shutdown();
        }
    }
    
    public boolean validateObject(final GearmanClient client) {
        return client != null && !client.isShutdown();
    }
}
