package com.demoproject.framework.thrift.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TMultiplexedProcessor;
import com.demoproject.framework.thrift.helper.THelper;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import java.net.InetSocketAddress;
import java.util.Set;
import org.apache.log4j.Logger;

public class TServerThreadedSelector implements TServerBuilder {

    private static final Logger logger;
    private final TServerConfig config;
    private final Set<TServerProcessor> processors;

    public TServerThreadedSelector(final TServerConfig config, final Set<TServerProcessor> processors) {
        this.config = config;
        this.processors = processors;
    }

    @Override
    public TServerObject getServer() {
        TServer server = null;
        try {
            final InetSocketAddress inetSock = new InetSocketAddress(this.config.host, this.config.port);
            final TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(inetSock);
            final TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args((TNonblockingServerTransport) serverTransport);
            args.protocolFactory(THelper.getProtocolFactory(this.config.protocol));
            args.transportFactory(THelper.getTransportFactory(this.config.framed));
            final TMultiplexedProcessor mc = new TMultiplexedProcessor();
            for (final TServerProcessor processor : this.processors) {
                mc.registerProcessor(processor.serviceClass.getCanonicalName(), THelper.getProcessor(processor.serviceClass, processor.processorClass));
            }
            args.processor((TProcessor) mc);
            args.workerThreads(this.config.maxThread);
            server = (TServer) new TThreadedSelectorServer(args);
        } catch (Exception ex) {
            TServerThreadedSelector.logger.error((Object) "getServer error", (Throwable) ex);
        }
        final TServerObject object = new TServerObject();
        object.host = this.config.host;
        object.port = this.config.port;
        object.server = server;
        return object;
    }

    static {
        logger = Logger.getLogger((Class) TServerHsHa.class);
    }
}
