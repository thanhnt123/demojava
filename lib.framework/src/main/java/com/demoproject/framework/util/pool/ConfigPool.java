package com.demoproject.framework.util.pool;

import java.util.Enumeration;
import org.apache.log4j.MDC;

public class ConfigPool {

    public static void setTrans(int trans) {
        MDC.put((String) "trans", (Object) trans);
    }

    public static void setTrans(long trans) {
        MDC.put((String) "trans", (Object) trans);
    }

    public static void clearTrans() {
        MDC.remove((String) "trans");
    }

    public static void setType(String type) {
        if (type == null) {
            MDC.remove((String) "type");
        } else {
            MDC.put((String) "type", (Object) type);
        }
    }

    public static void clearType() {
        MDC.remove((String) "type");
    }

    public static int count(Enumeration e) {
        int count = 0;
        while (e.hasMoreElements()) {
            ++count;
            e.nextElement();
        }
        return count;
    }
}
