package ru.netology.moneytransfer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.netology.moneytransfer.model.TransferInfo;
import ru.netology.moneytransfer.model.Confirmation;
import ru.netology.moneytransfer.model.SuccessResponse;
import ru.netology.moneytransfer.service.MoneyTransferService;

@RestController
@RequestMapping("/")
public class MoneyTransferController {
    private Logger log = LoggerFactory.getLogger(MoneyTransferController.class);
    private MoneyTransferService moneyTransferService;
    private final String frontUrl = "https://serp-ya.github.io/";
    public MoneyTransferController(
        @Value("${app.front.url") String frontUrl,
        MoneyTransferService moneyTransferService
    ) {
        // TODO: Use value from application.properties
        //this.frontUrl = frontUrl;
        this.moneyTransferService = moneyTransferService;
    }

    @PostMapping(value = "transfer", consumes = "application/json", produces = "application/json")
    @CrossOrigin(value = frontUrl)
    public SuccessResponse transfer(@RequestBody TransferInfo transferInfo) {
        moneyTransferService.registerNewTransfer(transferInfo);
        return new SuccessResponse();
    }

    @PostMapping(value = "confirmOperation", consumes = "application/json", produces = "application/json")
    @CrossOrigin(value = frontUrl)
    public SuccessResponse confirmOperation(@RequestBody Confirmation confirmation) {
        moneyTransferService.transferMoney(confirmation);
        return new SuccessResponse(confirmation.getOperationId());
    }
}
