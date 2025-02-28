package com.demoproject.framework.util;

import java.util.UUID;

public class UuidUtil
{
    public static String makeString() {
        return UUID.randomUUID().toString();
    }
    
    public static int makeInt() {
        return UUID.randomUUID().hashCode();
    }
}
