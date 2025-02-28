package com.demoproject.helper;

import com.demoproject.framework.util.ConfigUtil;
import com.demoproject.framework.util.ConvertUtil;


public class ConfigInfo {
    
    public static final String DEMO_DB = "demo_db";
    
    public static String TABLE_ACCOUNT = ConvertUtil.toString(ConfigUtil.getString(DEMO_DB, "account"), "account");
}
