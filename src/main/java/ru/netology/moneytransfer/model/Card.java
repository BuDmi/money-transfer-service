package ru.netology.moneytransfer.model;

import ru.netology.moneytransfer.exception.ErrorInputData;

import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Card {
    private final String number;
    private final String validTill;
    private final String cvc;
    private Double balance = 0.;

    public Card(String number, String validTill, String cvc, Double balance) {
        this.number = processCardNumber(number);
        this.validTill = processValidTill(validTill);
        this.cvc = processCvc(cvc);
        this.balance = balance;
    }

    public Card(String number, String validTill, String cvc) {
        this.number = processCardNumber(number);
        this.validTill = processValidTill(validTill);
        this.cvc = processCvc(cvc);
    }

    public Card(String number) {
        this.number = processCardNumber(number);
        this.validTill = null;
        this.cvc = null;
    }

    private String processCvc(String cvc) {
        if (cvc.isEmpty()) {
            throw new ErrorInputData("No value of cvc");
        }
        if (cvc.length() != 3) {
            throw new ErrorInputData("Cvc must have 3 numbers");
        }
        return cvc;
    }

    private String processValidTill(String validTill) {
        if (validTill.isEmpty()) {
            throw new ErrorInputData("No value of card valid till");
        }
        if (validTill.length() != 5) {
            throw new ErrorInputData("Card valid till must have 5 symbols");
        }

        YearMonth validDate = YearMonth.parse(validTill, DateTimeFormatter.ofPattern("MM/yy"));
        if (YearMonth.now(ZoneOffset.UTC).isAfter(validDate)) {
            throw new ErrorInputData("Card has expired");
        }
        int month = validDate.getMonthValue();
        if (month < 1 || month > 12) {
            throw new ErrorInputData("Invalid month value for card valid till");
        }
        return validTill;
    }

    private String processCardNumber(String number) {
        if (number.isEmpty()) {
            throw new ErrorInputData("No value of card number");
        }
        if (number.length() != 16) {
            throw new ErrorInputData("Card number must have 16 numbers");
        }
        return number;
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