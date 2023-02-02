package ru.netology.moneytransfer.service;

import org.springframework.stereotype.Component;
import ru.netology.moneytransfer.model.Card;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static ru.netology.moneytransfer.util.FileUtils.readCardsFromFile;

@Component
public class CardValidator {
    private final String pathFileCardDb = "src/main/resources/";
    private final String fileCardDbName = "card_db";
    private final ConcurrentHashMap<String, Card> registeredCards = readCardsFromFile(pathFileCardDb, fileCardDbName);

    public CardValidator() throws IOException {
    }

    public Boolean checkCardExistence(Card card) {
        return registeredCards.get(card.getNumber()) != null;
    }

    public Double getCardBalance(Card card) {
        return registeredCards.get(card.getNumber()).getBalance();
    }

    public void updateBalance(Card card, Double newBalance) {
        registeredCards.get(card.getNumber()).setBalance(newBalance);
    }

    public Boolean checkValidTill(Card card) {
        return registeredCards.get(card.getNumber()).getValidTill().equals(card.getValidTill());
    }

    public Boolean checkCardCvc(Card card) {
        return registeredCards.get(card.getNumber()).getCvc().equals(card.getCvc());
    }
}
