package ru.netology.moneytransfer.util;

import ru.netology.moneytransfer.model.RegisteredTransfer;
import ru.netology.moneytransfer.model.TransferInfo;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class GeneralUtils {
    private final static double PERCENT = 0.01;
    public static double calcFee(Integer value) {
        return new BigDecimal(value).multiply(BigDecimal.valueOf(PERCENT)).doubleValue();
    }

    //лог переводов в произвольном формате с указанием даты, времени, карта с которой было списание, карта зачисления,
    //сумма, комиссия, результат операции если был
    public static String getRegisteredTransferText(RegisteredTransfer registeredTransfer) {
        String time = registeredTransfer.getTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String status = registeredTransfer.isSuccess()? "Success" : "Fail";
        TransferInfo transferInfo = registeredTransfer.getTransferInfo();
        return time + " transfer from " + transferInfo.getCardFrom().getNumber() + " to " + transferInfo.getCardTo().getNumber()
            + " " + transferInfo.getAmount().getValue() + " " + transferInfo.getAmount().getCurrency() + " with fee "
            + calcFee(transferInfo.getAmount().getValue()) + " " + transferInfo.getAmount().getCurrency() + ": " + status;
    }
}
