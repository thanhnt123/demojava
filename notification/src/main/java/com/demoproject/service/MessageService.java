package com.demoproject.service;

import com.demoproject.entity.MessageEnt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmailService emailService;

    @KafkaListener(id = "notificationGroup", topics = "notification")
    public void listen(MessageEnt messageEnt) {
        logger.info("Received: ", messageEnt.getTo());
        //emailService.sendEmail(messageEnt);
    }
}
