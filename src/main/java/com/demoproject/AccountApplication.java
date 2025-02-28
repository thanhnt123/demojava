package com.demoproject;

import com.demoproject.framework.thrift.server.TServerFactory;
import com.demoproject.framework.thrift.server.TServerManager;
import com.demoproject.framework.thrift.server.TServerProcessor;
import com.demoproject.framework.util.LogUtil;
import com.demoproject.helper.ConfigInfo;
import com.demoproject.thrift.AccountProcessor;
import com.demoproject.thrift.account.AccountService;
import org.apache.log4j.Logger;

public class AccountApplication {

    private static final Logger logger;

    static {
        logger = LogUtil.getLogger(AccountApplication.class);
    }

    public static void main(String[] args) {
        TServerFactory tsf = new TServerFactory(ConfigInfo.THRIFT_SERVICE);
        tsf.addProcessor(new TServerProcessor(AccountService.class, AccountProcessor.class));

        TServerManager server = new TServerManager();
        server.add(tsf);
        server.start();
        logger.info("Thrift server Account start! ");
        logger.error("Thrift server start!");
        System.out.println("Thrift server start! ");
    }
}
