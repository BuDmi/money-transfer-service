package ru.netology.moneytransfer.model;

import ru.netology.moneytransfer.exception.ErrorInputData;

public class Amount {
    private Integer value;
    private String currency;
    private final double fee;

    private final double PERCENT = 0.01;

    public Amount(Integer value, String currency) {
        this.value = processValue(value);
        this.currency = currency;
        this.fee = (this.value / 100) * PERCENT;
    }

    private int processValue(Integer value) {
        if (value == null) {
            throw new ErrorInputData("Сумма перевода не указана");
        }
        if (value <= 0) {
            throw new ErrorInputData("Сумма перевода не может быть меньше или равна 0");
        }
        return value / 100;
    }

    public String getCurrency() {
        return currency;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Сумма перевода=" + value +
            ", Валюта='" + currency + '\'' +
            ", Комиссия=" + fee;
    }
}