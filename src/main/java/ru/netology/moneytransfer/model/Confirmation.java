package ru.netology.moneytransfer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Confirmation {
    private String operationId;
    private String code;

    public Confirmation(@JsonProperty("operationId") String operationId,
                        @JsonProperty("code") String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getCode() {
        return code;
    }

}
