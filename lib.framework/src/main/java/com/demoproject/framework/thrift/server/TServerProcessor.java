package com.demoproject.framework.thrift.server;

public class TServerProcessor
{
    public Class<?> serviceClass;
    public Class<?> processorClass;
    
    public TServerProcessor(final Class<?> serviceClass, final Class<?> processorClass) {
        this.serviceClass = serviceClass;
        this.processorClass = processorClass;
    }
}
