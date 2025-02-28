package com.demoproject.api;

import com.demoproject.entity.AccountEnt;
import com.demoproject.entity.MessageEnt;
import com.demoproject.entity.ReportEnt;
import com.demoproject.repo.AccountRepo;
import com.demoproject.repo.MessageRepo;
import com.demoproject.repo.ReportRepo;
import com.demoproject.thrift.AccountServiceAdapter;
import com.demoproject.thrift.account.TAccount;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    ReportRepo statisticRepo;

    @PostMapping("/new")
    public AccountEnt create(@RequestBody AccountEnt account) {

        TAccount accountItem = new TAccount();

        accountItem.setId(account.getId());
        accountItem.setUsername(account.getName());
        accountItem.setDisplayname(account.getName());
        accountItem.setAddress(account.getEmail());

        boolean isCreateUser = AccountServiceAdapter.createAccount(accountItem);

        if (isCreateUser) {
            ReportEnt stat = new ReportEnt("Account " + account.getName() + " is created", new Date());
            stat.setStatus(false);

            // send notificaiton
            MessageEnt messageEnt = new MessageEnt();
            messageEnt.setTo(account.getEmail());
            messageEnt.setToName(account.getName());
            messageEnt.setSubject("Welcome to demo project");
            messageEnt.setContent("Demo project.");
            messageEnt.setStatus(false);

            accountRepo.save(account);
            messageRepo.save(messageEnt);
            statisticRepo.save(stat);

            kafkaTemplate.send("notification", messageEnt).whenComplete(
                    (result, ex) -> {
                        if (ex == null) {
                            System.out.println("Sent message=[" + messageEnt.getId()
                                    + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                            messageEnt.setStatus(true);// success
                            messageRepo.save(messageEnt);
                        } else {
                            System.out.println("Unable to send message=["
                                    + messageEnt.getId() + "] due to : " + ex.getMessage());
                        }
                    }
            );

            kafkaTemplate.send("report", stat);
        }
        return account;
    }
}
