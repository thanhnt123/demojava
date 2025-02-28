package com.demoproject.framework.thrift.server;

import java.util.HashSet;
import java.util.Set;

public class TServerFactory implements TServerBuilder
{
    public TServerConfig config;
    public Set<TServerProcessor> processors;
    
    public TServerFactory(final String section) {
        this.processors = new HashSet<>();
        this.config = new TServerConfig(section);
    }
    
    public TServerFactory(final TServerConfig config) {
        this.processors = new HashSet<>();
        this.config = config;
    }
    
    public void addProcessor(final TServerProcessor processor) {
        this.processors.add(processor);
    }
    
    @Override
    public TServerObject getServer() {
        final String mode = this.config.mode;
        switch (mode) {
            case "nonblocking": {
                final TServerBuilder server = new TServerNonBlocking(this.config, this.processors);
                return server.getServer();
            }
            case "threadpool": {
                final TServerBuilder server = new TServerThreadPool(this.config, this.processors);
                return server.getServer();
            }
            case "threadedselector": {
                final TServerBuilder server = new TServerThreadedSelector(this.config, this.processors);
                return server.getServer();
            }
            default: {
                final TServerBuilder server = new TServerHsHa(this.config, this.processors);
                return server.getServer();
            }
        }
    }
}
