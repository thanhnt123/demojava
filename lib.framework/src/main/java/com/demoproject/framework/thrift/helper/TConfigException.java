package com.demoproject.framework.thrift.helper;

public final class TConfigException extends RuntimeException
{
    public TConfigException() {
    }
    
    public TConfigException(final String message) {
        super(message);
    }
    
    public TConfigException(final Throwable e) {
        super(e);
    }
    
    public TConfigException(final String message, final Throwable e) {
        super(message, e);
    }
}
