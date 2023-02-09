package com.boutiqaat.catalogadminexportimportplus.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Message {
    @JsonProperty("code")
    private String code;

    @JsonProperty("technicalErrorMessage")
    private String technicalErrorMessage;

    @JsonProperty("displayErrorMessage")
    private String displayErrorMessage;

    @JsonProperty("displayMessage")
    private String displayMessage;

    @JsonProperty("message")
    private String message;

    @JsonProperty("developerMessage")
    private String developerMessage;

    @JsonProperty("success")
    private String success;

    @JsonProperty("error")
    private String error;


    public Message(String code, String techErrMsg, String displayErrMsg) {
        this.code = code;
        this.technicalErrorMessage = techErrMsg;
        this.displayErrorMessage = displayErrMsg;
    }

    public Message(String message) {
        this.displayMessage = message;
        this.developerMessage = message;

    }

    public Message(String success, String err) {
        this.success = success;
        this.error = err;
    }

}
