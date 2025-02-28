package com.demoproject.helper;

import com.demoproject.framework.util.ConfigUtil;
import com.demoproject.framework.util.ConvertUtil;

public class ConfigInfo {
    
    public static final String THRIFT_SERVICE = "thrift_service";
    
    /**
     * gearman.
     */
    public static final String GEARMAN_SERVER = "gearman_server";
    
    /**
     * function.
     */
    public static final String FUNCTION_ACCOUNT = ConvertUtil.toString(ConfigUtil.getString(GEARMAN_SERVER, "function_account"), "account");
}
