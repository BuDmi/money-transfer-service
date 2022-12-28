package ru.netology.moneytransfer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

public class TransferInfo {
    private final Card cardFrom;
    private final Card cardTo;
    private final Amount amount;
    private final Integer operationId;


    public TransferInfo(@JsonProperty("cardFromNumber") String fromNumber,
                        @JsonProperty("cardFromValidTill") String validTill,
                        @JsonProperty("cardFromCVV") String cardCVV,
                        @JsonProperty("cardToNumber") String toNumber,
                        @JsonProperty("amount") Amount amount) {
        this.cardFrom = new Card(fromNumber, validTill, cardCVV);
        this.cardTo = new Card(toNumber);
        this.amount = amount;
        this.operationId = generateCode();
    }

    public static Integer generateCode() {
        Random rand = new Random();
        return rand.nextInt(Integer.MAX_VALUE);
    }

    public Card getCardFrom() {
        return cardFrom;
    }

    public Card getCardTo() {
        return cardTo;
    }

    public Amount getAmount() {
        return amount;
    }

    public Integer getOperationId() {
        return operationId;
    }
}
