package ru.netology.moneytransfer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.netology.moneytransfer.model.CardTransfer;

@RestController
@RequestMapping("/")
public class CardController {
    private Logger log = LoggerFactory.getLogger(CardController.class);
    private final String frontUrl = "https://serp-ya.github.io/";
    public CardController(@Value("${app.front.url") String frontUrl) {
        //this.frontUrl = frontUrl;
        // TODO: Use value from application.properties
    }

    @PostMapping(value = "transfer", consumes = "application/json", produces = "application/json")
    @CrossOrigin(value = frontUrl)
    public String transfer(@RequestBody CardTransfer cardTransfer) {
        return "";
    }

    @PostMapping(value = "confirmOperation", consumes = "application/json", produces = "application/json")
    @CrossOrigin("https://serp-ya.github.io/")
    public String confirmOperation(@RequestBody Object obj) {
        return "";
    }
}
