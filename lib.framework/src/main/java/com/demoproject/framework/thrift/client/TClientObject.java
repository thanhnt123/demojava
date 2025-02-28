package com.demoproject.framework.thrift.client;

import org.apache.thrift.transport.TTransport;

public class TClientObject<T>
{
    public TTransport transport;
    public T client;
}
