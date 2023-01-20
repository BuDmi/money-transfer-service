package ru.netology.moneytransfer.model;

import ru.netology.moneytransfer.exception.ErrorInputData;

import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Card {
    private final String number;
    private final String validTill;
    private final String cvc;
    private Double balance;

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
        this.balance = 0.;
    }

    public Card(String number) {
        this.number = processCardNumber(number);
        this.validTill = null;
        this.cvc = null;
        this.balance = null;
    }

    private String processCvc(String cvc) {
        if (cvc.isEmpty()) {
            System.out.println("No value of cvc");
            throw new ErrorInputData("No value of cvc");
        }
        if (cvc.length() != 3) {
            System.out.println("Cvc must have 3 numbers");
            throw new ErrorInputData("Cvc must have 3 numbers");
        }
        return cvc;
    }

    private String processValidTill(String validTill) {
        if (validTill.isEmpty()) {
            System.out.println("No value of card valid till");
            throw new ErrorInputData("No value of card valid till");
        }
        if (validTill.length() != 5) {
            System.out.println("Card valid till must have 5 symbols");
            throw new ErrorInputData("Card valid till must have 5 symbols");
        }

        YearMonth validDate = YearMonth.parse(validTill, DateTimeFormatter.ofPattern("MM/yy"));
        if (YearMonth.now(ZoneOffset.UTC).isAfter(validDate)) {
            System.out.println("Card has expired");
            throw new ErrorInputData("Card has expired");
        }
        int month = validDate.getMonthValue();
        if (month < 1 || month > 12) {
            System.out.println("Invalid month value for card valid till");
            throw new ErrorInputData("Invalid month value for card valid till");
        }
        return validTill;
    }

    private String processCardNumber(String number) {
        if (number.isEmpty()) {
            System.out.println("No value of card number");
            throw new ErrorInputData("No value of card number");
        }
        if (number.length() != 16) {
            System.out.println("Сard number must have 16 numbers");
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