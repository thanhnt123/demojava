package com.demoproject.framework.thrift.helper;

import java.lang.reflect.Constructor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TTransportFactory;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

public class THelper {

    public static TProtocolFactory getProtocolFactory(final String protocol) {
        TProtocolFactory tpf = null;
        switch (protocol) {
            case "compact": {
                tpf = (TProtocolFactory) new TCompactProtocol.Factory();
                break;
            }
            case "json": {
                tpf = (TProtocolFactory) new TJSONProtocol.Factory();
                break;
            }
            default: {
                tpf = (TProtocolFactory) new TBinaryProtocol.Factory();
                break;
            }
        }
        return tpf;
    }

    public static TProtocol getProtocol(final String protocol, final TTransport transport) {
        TProtocol tp = null;
        switch (protocol) {
            case "compact": {
                tp = (TProtocol) new TCompactProtocol(transport);
                break;
            }
            case "json": {
                tp = (TProtocol) new TJSONProtocol(transport);
                break;
            }
            default: {
                tp = (TProtocol) new TBinaryProtocol(transport);
                break;
            }
        }
        return tp;
    }

    public static TTransportFactory getTransportFactory(final boolean framed) {
        if (framed) {
            return (TTransportFactory) new TFramedTransport.Factory();
        }
        return new TTransportFactory();
    }

    private static Class<?> extractInnerClass(final Class<?> serviceClass, final InnerClasses kind) {
        final Class<?>[] classes2;
        final Class<?>[] classes = classes2 = serviceClass.getClasses();
        for (final Class<?> aClass : classes2) {
            if (kind.matches(aClass)) {
                return aClass;
            }
        }
        throw new IllegalArgumentException("unable to find inner class: " + kind.name() + " inside service class: " + serviceClass.getName());
    }

    public static TProcessor getProcessor(final Class<?> serviceClass, final Class<?> processorClass) throws Exception {
        final Class<?> ifaceClass = extractInnerClass(serviceClass, InnerClasses.Iface);
        final Class<TProcessor> processor = (Class<TProcessor>) extractInnerClass(serviceClass, InnerClasses.Processor);
        final Constructor<?> processorConstructor = processor.getConstructor(ifaceClass);
        return (TProcessor) processorConstructor.newInstance(processorClass.newInstance());
    }

    public static Object getClient(final Class<?> serviceClass, final TProtocol protocol) throws Exception {
        final Class<?> clientClass = extractInnerClass(serviceClass, InnerClasses.Client);
        final Constructor<?> clientConstructor = clientClass.getConstructor(TProtocol.class);
        return clientConstructor.newInstance(protocol);
    }

    enum InnerClasses {
        Client,
        Iface,
        Processor;

        public boolean matches(final Class<?> clazz) {
            return this.name().equals(clazz.getSimpleName());
        }
    }
}
