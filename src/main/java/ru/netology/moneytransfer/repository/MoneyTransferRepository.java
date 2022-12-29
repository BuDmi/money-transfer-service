package ru.netology.moneytransfer.repository;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransfer.exception.ErrorTransfer;
import ru.netology.moneytransfer.logger.Logger;
import ru.netology.moneytransfer.logger.TextFileLogger;
import ru.netology.moneytransfer.model.Card;
import ru.netology.moneytransfer.model.TransferInfo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import static ru.netology.moneytransfer.util.FileUtils.readCardsFromFile;

@Repository
public class MoneyTransferRepository {
    private final String pathFileLogger = "src/main/resources/";
    private final String fileLoggerName = "file.log";
    private final String fileCardDbName = "card_db";
    private final Logger textFileLogger = new TextFileLogger(pathFileLogger, fileLoggerName);
    private final ConcurrentHashMap<String, Card> registeredCards = readCardsFromFile(pathFileLogger, fileCardDbName);
    private final ArrayBlockingQueue<TransferInfo> transferQueue = new ArrayBlockingQueue<>(1);

    public MoneyTransferRepository() throws IOException {
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

    @SneakyThrows
    public void registerNewTransfer(TransferInfo transferInfo, Boolean successTransfer) {
        if (successTransfer) {
            transferQueue.put(transferInfo);
        } else {
            textFileLogger.logNewMessage(getTransferInfoText(transferInfo, successTransfer));
        }
    }

    public void transferMoney(Boolean successTransfer) {
        TransferInfo transferInfo;
        try {
            transferInfo = transferQueue.take();
        } catch (InterruptedException e) {
            throw new ErrorTransfer("Ошибка при переводе денег");
        }
        textFileLogger.logNewMessage(getTransferInfoText(transferInfo, successTransfer));
    }

    //лог переводов в произвольном формате с указанием даты, времени, карта с которой было списание, карта зачисления,
    //сумма, комиссия, результат операции если был
    private String getTransferInfoText(TransferInfo transferInfo, Boolean successTransfer) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String status = successTransfer? "Success" : "Fail";
        return time + " transfer from " + transferInfo.getCardFrom().getNumber() + " to " + transferInfo.getCardTo().getNumber()
            + " " + transferInfo.getAmount().getValue() + " " + transferInfo.getAmount().getCurrency() + " with fee "
            + transferInfo.getAmount().getFee() + " " + transferInfo.getAmount().getCurrency() + ": " + status;
    }
}
