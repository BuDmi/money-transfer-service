package ru.netology.moneytransfer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferInfo {
    private final Card cardFrom;
    private final Card cardTo;
    private final Amount amount;

    public TransferInfo(@JsonProperty("cardFromNumber") String fromNumber,
                        @JsonProperty("cardFromValidTill") String validTill,
                        @JsonProperty("cardFromCVV") String cardCVV,
                        @JsonProperty("cardToNumber") String toNumber,
                        @JsonProperty("amount") Amount amount) {
        this.cardFrom = new Card(fromNumber, validTill, cardCVV);
        this.cardTo = new Card(toNumber);
        this.amount = amount;
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
}
