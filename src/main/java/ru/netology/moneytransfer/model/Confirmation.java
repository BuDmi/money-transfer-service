package ru.netology.moneytransfer.model;

public class Confirmation {
    private Integer operationId;
    private String code;

    public Confirmation(Integer operationId,
                        String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
