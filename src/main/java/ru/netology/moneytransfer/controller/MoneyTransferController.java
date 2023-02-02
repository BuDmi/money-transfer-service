package ru.netology.moneytransfer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.moneytransfer.model.TransferInfo;
import ru.netology.moneytransfer.model.Confirmation;
import ru.netology.moneytransfer.model.SuccessResponse;
import ru.netology.moneytransfer.service.MoneyTransferService;

@RestController
public class MoneyTransferController {
    private final Logger log = LoggerFactory.getLogger(MoneyTransferController.class);
    private final MoneyTransferService moneyTransferService;
    private final String frontUrl = "https://serp-ya.github.io/";
    public MoneyTransferController(
        MoneyTransferService moneyTransferService
    ) {
        this.moneyTransferService = moneyTransferService;
    }

    @PostMapping(value = "transfer")
    @CrossOrigin(value = frontUrl)
    public ResponseEntity<SuccessResponse> transfer(@RequestBody TransferInfo transferInfo) {
        moneyTransferService.registerNewTransfer(transferInfo);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "confirmOperation")
    @CrossOrigin(value = frontUrl)
    public ResponseEntity<SuccessResponse> confirmOperation(@RequestBody Confirmation confirmation) {
        moneyTransferService.transferMoney(confirmation);
        return new ResponseEntity<>(new SuccessResponse(confirmation.getOperationId()), HttpStatus.OK);
    }
}
