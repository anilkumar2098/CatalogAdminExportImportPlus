package com.boutiqaat.catalogadminexportimportplus.common;


import lombok.AllArgsConstructor;

/*
@author Ramandeep Singh
*/

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class Status {
    private String httpCode;
    private Boolean success;
    private List<Message> messages;

    public Status() {
        this.httpCode = "success";
        this.success = true;
        this.messages = Arrays.asList(new Message("200", "", "Data Successfully retrieved"));
    }

    public Status(String message) {
        this.httpCode = "200";
        this.success = true;
        Message msg = new Message(message);
        this.messages = new ArrayList<Message>();
        this.messages.add(msg);
    }

    public Status(String msg1, String msg2) {
        this.httpCode = "200";
        this.success = true;
        this.messages = Arrays.asList(new Message(msg1, msg2));
    }

    public Status(Integer code, String msg1, String msg2) {
        this.httpCode = "" + code;
        this.success = false;
        this.messages = Arrays.asList(new Message(null, msg1, msg2));
    }

}