package ru.netology.moneytransfer.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class Card {
    @NotBlank
    @Length(min = 16, max = 16)
    private final String number;
    @NotBlank
    @Length(min = 5, max = 5)
    private final String validTill;
    @NotBlank
    @Length(min = 3, max = 3)
    private final String cvc;
    private Double balance;

    public Card(String number, String validTill, String cvc, Double balance) {
        this.number = number;
        this.validTill = validTill;
        this.cvc = cvc;
        this.balance = balance;
    }

    public Card(String number, String validTill, String cvc) {
        this.number = number;
        this.validTill = validTill;
        this.cvc = cvc;
        this.balance = 0.;
    }

    public Card(String number) {
        this.number = number;
        this.validTill = null;
        this.cvc = null;
        this.balance = null;
    }

    @Override
    public String toString() {
        return "Карта{" +
            "номер='" + number + '\'' +
            ", срок действия='" + validTill + '\'' +
            ", CVC='" + cvc + '\'' +
            '}';
    }

    public String getNumber() {
        return number;
    }

    public String getValidTill() {
        return validTill;
    }

    public String getCvc() {
        return cvc;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}