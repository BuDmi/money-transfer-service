package ru.netology.moneytransfer.model;

public class ErrorResponse {
    private final String message;
    private final Integer id;

    public ErrorResponse(String message, Integer id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public Integer getId() {
        return id;
    }
}
