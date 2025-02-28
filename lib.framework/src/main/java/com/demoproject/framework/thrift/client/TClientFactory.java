package com.demoproject.framework.thrift.client;

import org.apache.thrift.protocol.TProtocol;

public interface TClientFactory<T>
{
    T make(final TProtocol p0);
    
    boolean ping(final T p0);
}
