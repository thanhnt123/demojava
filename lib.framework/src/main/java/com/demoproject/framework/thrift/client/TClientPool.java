package com.demoproject.framework.thrift.client;

import java.lang.reflect.Method;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.log4j.Logger;
import org.apache.commons.pool.BasePoolableObjectFactory;
import com.demoproject.framework.thrift.helper.THelper;

public class TClientPool<T> extends BasePoolableObjectFactory<TClientObject<T>> {

    private static final Logger logger;
    private final String host;
    private final int port;
    private final int timeout;
    private final boolean framed;
    private final String protocol;
    private final boolean ping;
    private final Class<?> serviceClass;

    public TClientPool(final String host, final int port, final int timeout, final boolean framed, final String protocol, final boolean ping, final Class<?> serviceClass) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.framed = framed;
        this.protocol = protocol;
        this.ping = ping;
        this.serviceClass = serviceClass;
    }

    public TClientObject<T> makeObject() throws Exception {
        TTransport transport;
        if (this.framed) {
            transport = (TTransport) new TFramedTransport((TTransport) new TSocket(this.host, this.port, this.timeout));
        } else {
            transport = (TTransport) new TSocket(this.host, this.port, this.timeout);
        }
        transport.open();
        final T client = this.getInstanceClient(transport);
        final TClientObject<T> tco = new TClientObject<T>();
        tco.transport = transport;
        tco.client = client;
        return tco;
    }

    public void destroyObject(final TClientObject<T> tclient) {
        if (tclient == null) {
            return;
        }
        if (tclient.transport == null) {
            return;
        }
        if (tclient.transport.isOpen()) {
            tclient.transport.close();
        }
    }

    public boolean validateObject(final TClientObject<T> tco) {
        return this.isAlive(tco);
    }

    private T getInstanceClient(final TTransport transport) throws Exception {
        final TProtocol tp = THelper.getProtocol(this.protocol, transport);
        final TMultiplexedProtocol mp = new TMultiplexedProtocol(tp, this.serviceClass.getCanonicalName());
        final T client = (T) THelper.getClient(this.serviceClass, (TProtocol) mp);
        return client;
    }

    private boolean isAlive(final TClientObject<T> tco) {
        if (tco == null) {
            return false;
        }
        if (tco.transport == null) {
            return false;
        }
        if (!tco.transport.isOpen()) {
            return false;
        }
        if (!this.ping) {
            return true;
        }
        boolean pingServer = false;
        try {
            final T client = this.getInstanceClient(tco.transport);
            final Method method = client.getClass().getMethod("ping", (Class<?>[]) new Class[0]);
            pingServer = (boolean) method.invoke(client, new Object[0]);
        } catch (Exception ex) {
            TClientPool.logger.warn((Object) "ping fail", (Throwable) ex);
        }
        return pingServer;
    }

    static {
        logger = Logger.getLogger((Class) TClientPool.class);
    }
}
