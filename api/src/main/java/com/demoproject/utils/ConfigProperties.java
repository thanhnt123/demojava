package com.demoproject.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:application.properties"})
public class ConfigProperties {

    @Autowired
    private Environment env;

    public String getConfigValue(final String configKey) {
        return this.env.getProperty(configKey);
    }
}
