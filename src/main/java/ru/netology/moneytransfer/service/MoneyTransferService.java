package ru.netology.moneytransfer.service;

import org.springframework.stereotype.Service;
import ru.netology.moneytransfer.exception.ErrorConfirmation;
import ru.netology.moneytransfer.exception.ErrorTransfer;
import ru.netology.moneytransfer.logger.Logger;
import ru.netology.moneytransfer.logger.TextFileLogger;
import ru.netology.moneytransfer.model.Confirmation;
import ru.netology.moneytransfer.model.TransferInfo;
import ru.netology.moneytransfer.repository.MoneyTransferRepository;

import static ru.netology.moneytransfer.util.GeneralUtils.calcFee;
import static ru.netology.moneytransfer.util.GeneralUtils.getRegisteredTransferText;

@Service
public class MoneyTransferService {

    private final String pathFileLogger = "src/main/resources/";
    private final String fileLoggerName = "file.log";
    private final Logger textFileLogger = new TextFileLogger(pathFileLogger, fileLoggerName);
    private final MoneyTransferRepository moneyTransferRepository;
    private final CardValidator cardValidator;

    public MoneyTransferService(
        MoneyTransferRepository moneyTransferRepository,
        CardValidator cardValidator
    ) {
        this.moneyTransferRepository = moneyTransferRepository;
        this.cardValidator = cardValidator;
    }

    public void registerNewTransfer(TransferInfo transferInfo) {
        if (!cardValidator.checkCardExistence(transferInfo.getCardFrom())) {
            moneyTransferRepository.registerNewTransfer(transferInfo, false);
            textFileLogger.logNewMessage(getRegisteredTransferText(moneyTransferRepository.getLastRegisteredTransfer()));
            throw new ErrorTransfer("Несуществующий номер карты отправителя");
        }
        if (!cardValidator.checkCardExistence(transferInfo.getCardTo())) {
            moneyTransferRepository.registerNewTransfer(transferInfo, false);
            textFileLogger.logNewMessage(getRegisteredTransferText(moneyTransferRepository.getLastRegisteredTransfer()));
            throw new ErrorTransfer("Несуществующий номер карты получателя");
        }
        if (!cardValidator.checkValidTill(transferInfo.getCardFrom())) {
            moneyTransferRepository.registerNewTransfer(transferInfo, false);
            textFileLogger.logNewMessage(getRegisteredTransferText(moneyTransferRepository.getLastRegisteredTransfer()));
            throw new ErrorTransfer("Неверный срок действия карты");
        }
        if (!cardValidator.checkCardCvc(transferInfo.getCardFrom())) {
            moneyTransferRepository.registerNewTransfer(transferInfo, false);
            textFileLogger.logNewMessage(getRegisteredTransferText(moneyTransferRepository.getLastRegisteredTransfer()));
            throw new ErrorTransfer("Неверный CVC");
        }
        double paidSum = transferInfo.getAmount().getValue() + calcFee(transferInfo.getAmount().getValue());
        double curBalance = cardValidator.getCardBalance(transferInfo.getCardFrom());
        if (curBalance < paidSum) {
            moneyTransferRepository.registerNewTransfer(transferInfo, false);
            textFileLogger.logNewMessage(getRegisteredTransferText(moneyTransferRepository.getLastRegisteredTransfer()));
            throw new ErrorTransfer("Недостаточно денег на карте отправителя");
        }
        cardValidator.updateBalance(transferInfo.getCardFrom(), curBalance - paidSum);
        moneyTransferRepository.registerNewTransfer(transferInfo, true);
    }

    public void transferMoney(Confirmation confirmation) {
        String verificationCode = "0000";
        if (confirmation.getCode().equals(verificationCode)) {
            moneyTransferRepository.transferMoney(true);
            textFileLogger.logNewMessage(getRegisteredTransferText(moneyTransferRepository.getLastRegisteredTransfer()));
        } else {
            moneyTransferRepository.transferMoney(false);
            textFileLogger.logNewMessage(getRegisteredTransferText(moneyTransferRepository.getLastRegisteredTransfer()));
            throw new ErrorConfirmation("Ошибка при проверке кода подтверждения");
        }
    }
}
