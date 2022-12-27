package ru.netology.moneytransfer.exception;

public class ErrorConfirmation extends RuntimeException {
    public ErrorConfirmation(String msg) {
        super(msg);
    }
}
