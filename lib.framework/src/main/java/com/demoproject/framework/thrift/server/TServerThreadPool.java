package com.demoproject.framework.thrift.server;

import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TMultiplexedProcessor;
import com.demoproject.framework.thrift.helper.THelper;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import java.net.InetSocketAddress;
import java.util.Set;
import org.apache.log4j.Logger;

public class TServerThreadPool implements TServerBuilder {

    private static final Logger logger;
    private final TServerConfig config;
    private final Set<TServerProcessor> processors;

    public TServerThreadPool(final TServerConfig config, final Set<TServerProcessor> processors) {
        this.config = config;
        this.processors = processors;
    }

    @Override
    public TServerObject getServer() {
        TServer server = null;
        try {
            final InetSocketAddress inetSock = new InetSocketAddress(this.config.host, this.config.port);
            final TServerTransport serverTransport = (TServerTransport) new TServerSocket(inetSock);
            final TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
            args.protocolFactory(THelper.getProtocolFactory(this.config.protocol));
            args.transportFactory(THelper.getTransportFactory(this.config.framed));
            final TMultiplexedProcessor mc = new TMultiplexedProcessor();
            for (final TServerProcessor processor : this.processors) {
                mc.registerProcessor(processor.serviceClass.getCanonicalName(), THelper.getProcessor(processor.serviceClass, processor.processorClass));
            }
            args.processor((TProcessor) mc);
            args.minWorkerThreads(this.config.minThread);
            args.maxWorkerThreads(this.config.maxThread);
            server = (TServer) new TThreadPoolServer(args);
        } catch (Exception ex) {
            TServerThreadPool.logger.error((Object) "getServer error", (Throwable) ex);
        }
        final TServerObject object = new TServerObject();
        object.host = this.config.host;
        object.port = this.config.port;
        object.server = server;
        return object;
    }

    static {
        logger = Logger.getLogger((Class) TServerNonBlocking.class);
    }
}
