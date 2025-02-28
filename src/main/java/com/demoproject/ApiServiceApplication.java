package com.demoproject;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAutoConfiguration
@SpringBootApplication
@EnableConfigurationProperties
public class ApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiServiceApplication.class, args);
    }

    @Bean
    NewTopic notification() {
        // topic name, partition numbers, replication number
        return new NewTopic("notification", 2, (short) 1);
    }

    @Bean
    NewTopic statistic() {
        // topic name, partition numbers, replication number
        return new NewTopic("report", 1, (short) 1);
    }
}
