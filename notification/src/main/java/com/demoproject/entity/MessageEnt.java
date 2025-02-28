package com.demoproject.entity;

import lombok.Data;

@Data
public class MessageEnt {

    private String to;
    private String toName;
    private String subject;
    private String content;
}
