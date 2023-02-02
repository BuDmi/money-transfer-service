package ru.netology.moneytransfer.repository;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransfer.exception.ErrorTransfer;
import ru.netology.moneytransfer.model.RegisteredTransfer;
import ru.netology.moneytransfer.model.TransferInfo;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.netology.moneytransfer.util.GeneralUtils.calcFee;

@Repository
public class MoneyTransferRepository {
    private final ArrayBlockingQueue<TransferInfo> transferQueue = new ArrayBlockingQueue<>(1);
    private final CopyOnWriteArrayList<RegisteredTransfer> registeredTransfers = new CopyOnWriteArrayList<>();

    @SneakyThrows
    public void registerNewTransfer(TransferInfo transferInfo, Boolean successTransfer) {
        if (successTransfer) {
            transferQueue.put(transferInfo);
        } else {
            registeredTransfers.add(
                new RegisteredTransfer(
                    LocalDateTime.now(),
                    transferInfo,
                    calcFee(transferInfo.getAmount().getValue()),
                    successTransfer
                )
            );
        }
    }

    public RegisteredTransfer getLastRegisteredTransfer() {
        return registeredTransfers.get(registeredTransfers.size() - 1);
    }

    public void transferMoney(Boolean successTransfer) {
        TransferInfo transferInfo;
        try {
            transferInfo = transferQueue.take();
        } catch (InterruptedException e) {
            throw new ErrorTransfer("Ошибка при переводе денег");
        }
        registeredTransfers.add(
            new RegisteredTransfer(
                LocalDateTime.now(),
                transferInfo,
                calcFee(transferInfo.getAmount().getValue()),
                successTransfer
            )
        );
    }
}
