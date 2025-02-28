package com.demoproject.utils;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CustomPort implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        if (Configure.PORT != null) {
            System.out.println("Configure.PORT:" + Configure.PORT);
            factory.setPort((int) Integer.valueOf(Configure.PORT));
        }
    }
}
