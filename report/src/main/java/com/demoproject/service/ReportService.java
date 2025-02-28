package com.demoproject.service;

import com.demoproject.entity.ReportEnt;
import com.demoproject.repo.ReportRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReportRepo reportRepo;

    @KafkaListener(id = "reportGroup", topics = "report")
    public void listen(ReportEnt reportEnt) {
        logger.info("Received: " + reportEnt.getMessage());
        reportRepo.save(reportEnt);
    }

    @KafkaListener(id = "dltGroup3", topics = "reportEnt.DLT")
    public void dltListen(String in) {
        logger.info("Received from DLT: ");
        System.out.println(in);
    }
}
