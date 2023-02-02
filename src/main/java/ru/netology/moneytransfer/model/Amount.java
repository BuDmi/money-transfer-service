package ru.netology.moneytransfer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class Amount {
    @NotNull
    @Positive
    private final Integer value;
    private final String currency;

    @JsonCreator
    public Amount(Integer value, String currency) {
        this.value = value / 100;
        this.currency = currency;
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
            ", Валюта='" + currency + '\'';
    }
}