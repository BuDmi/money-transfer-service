package ru.netology.moneytransfer.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisteredTransfer {
    private final LocalDateTime time;
    private final TransferInfo transferInfo;
    private final double fee;
    private final boolean success;
}
