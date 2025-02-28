package com.demoproject.framework.thrift.util;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.TBase;
import org.apache.log4j.Logger;

public class ThriftSerializer {

    private static final Logger logger;

    public static <T extends TBase> byte[] serialize(final T value) {
        try {
            final TSerializer serializer = new TSerializer((TProtocolFactory) new TCompactProtocol.Factory());
            return serializer.serialize((TBase) value);
        } catch (TException ex) {
            ThriftSerializer.logger.error((Object) ("can't serialize object: " + ex.getMessage()));
            return null;
        }
    }

    public static <T extends TBase> T deserialize(final byte[] data, final Class<T> type) {
        try {
            final TDeserializer deserializer = new TDeserializer((TProtocolFactory) new TCompactProtocol.Factory());
            final TBase item = type.newInstance();
            deserializer.deserialize(item, data);
            return (T) item;
        } catch (InstantiationException | IllegalAccessException | TException ex3) {
            final Exception ex2;
            final Exception ex = ex3;
            ThriftSerializer.logger.error((Object) ("can't deserialize object: " + ex.getMessage()));
            return null;
        }
    }

    static {
        logger = Logger.getLogger((Class) ThriftSerializer.class);
    }
}
