package com.demoproject.framework.gearman;

import com.demoproject.framework.util.UuidUtil;

public class JobEnt<T extends Enum>
{
    public String uuid;
    public T type;
    public String data;
    public long createdAt;
    
    public JobEnt() {
        this.uuid = UuidUtil.makeString();
        this.data = "";
        this.createdAt = System.currentTimeMillis();
    }
    
    public JobEnt(final String uuid, final T type, final String data, final long createdAt) {
        this.uuid = uuid;
        this.type = type;
        this.data = data;
        this.createdAt = createdAt;
    }
}
