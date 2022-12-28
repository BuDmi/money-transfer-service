package ru.netology.moneytransfer.service;

import org.springframework.stereotype.Service;
import ru.netology.moneytransfer.exception.ErrorConfirmation;
import ru.netology.moneytransfer.exception.ErrorTransfer;
import ru.netology.moneytransfer.model.Confirmation;
import ru.netology.moneytransfer.model.TransferInfo;
import ru.netology.moneytransfer.repository.MoneyTransferRepository;

@Service
public class MoneyTransferService {
    private final MoneyTransferRepository moneyTransferRepository;

    public MoneyTransferService(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }

    public void registerNewTransfer(TransferInfo transferInfo) {
        if (!moneyTransferRepository.checkCardExistence(transferInfo.getCardFrom())) {
            moneyTransferRepository.registerNewTransfer(transferInfo, false);
            throw new ErrorTransfer("Non-existed number of card from");
        }
        if (!moneyTransferRepository.checkCardExistence(transferInfo.getCardTo())) {
            moneyTransferRepository.registerNewTransfer(transferInfo, false);
            throw new ErrorTransfer("Non-existed number of card to");
        }
        double paidSum = transferInfo.getAmount().getValue() + transferInfo.getAmount().getFee();
        double curBalance = transferInfo.getCardFrom().getBalance();
        if (curBalance < paidSum) {
            moneyTransferRepository.registerNewTransfer(transferInfo, false);
            throw new ErrorTransfer("Not enough money on card from");
        }
        transferInfo.getCardFrom().setBalance(curBalance - paidSum);
        moneyTransferRepository.registerNewTransfer(transferInfo, true);
    }

    public void transferMoney(Confirmation confirmation) {
        String verificationCode = "0000";
        if (confirmation.getCode().equals(verificationCode)) {
            moneyTransferRepository.transferMoney(true);
        } else {
            moneyTransferRepository.transferMoney(false);
            throw new ErrorConfirmation("Failed to confirm code");
        }
    }
}
