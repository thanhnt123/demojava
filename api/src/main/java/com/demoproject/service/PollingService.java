package com.demoproject.service;

import com.demoproject.entity.MessageEnt;
import com.demoproject.entity.ReportEnt;
import com.demoproject.repo.MessageRepo;
import com.demoproject.repo.ReportRepo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PollingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    ReportRepo reportRepo;

    @Scheduled(fixedDelay = 1000)
    public void producer() {
        List<MessageEnt> messageEnts = messageRepo.findByStatus(false);

        for (MessageEnt messageEnt : messageEnts) {
            kafkaTemplate.send("notification", messageEnt).whenComplete(
                    (result, ex) -> {
                        if (ex == null) {
                            logger.info("Sent message=[" + messageEnt.getId()
                                    + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                            messageEnt.setStatus(true);// success
                            messageRepo.save(messageEnt);
                        } else {
                            logger.error("Unable to send message=["
                                    + messageEnt.getId() + "] due to : " + ex.getMessage());
                        }
                    }
            );
        }

        List<ReportEnt> reports = reportRepo.findByStatus(false);
        for (ReportEnt reportEnt : reports) {
            kafkaTemplate.send("report", reportEnt).whenComplete(
                    (result, ex) -> {
                        if (ex == null) {
                            logger.info("Sent message=[" + reportEnt.getId()
                                    + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                            reportEnt.setStatus(true);// success
                            reportRepo.save(reportEnt);
                        } else {
                            logger.error("Unable to send message=["
                                    + reportEnt.getId() + "] due to : " + ex.getMessage());
                        }
                    }
            );
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void delete() {
        List<MessageEnt> messageDTOs = messageRepo.findByStatus(true);
        messageRepo.deleteAllInBatch(messageDTOs);

        List<ReportEnt> statisticDTOs = reportRepo.findByStatus(true);
        reportRepo.deleteAllInBatch(statisticDTOs);
    }
}
