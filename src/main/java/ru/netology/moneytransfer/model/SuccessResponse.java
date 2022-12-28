package ru.netology.moneytransfer.model;

import java.util.UUID;

public class SuccessResponse {
    private final String operationId;
    public SuccessResponse() {
        this.operationId = UUID.randomUUID().toString();
    }

    public SuccessResponse(String operationId) {
        this.operationId = operationId;
    }

    public String getOperationId() {
        return operationId;
    }
}
