package ru.netology.moneytransfer.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransfer.exception.ErrorConfirmation;
import ru.netology.moneytransfer.exception.ErrorInputData;
import ru.netology.moneytransfer.exception.ErrorTransfer;
import ru.netology.moneytransfer.model.ErrorResponse;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<ErrorResponse> eidHandler(ErrorInputData e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ErrorTransfer.class)
    public ResponseEntity<ErrorResponse> etHandler(ErrorTransfer e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ErrorConfirmation.class)
    public ResponseEntity<ErrorResponse> ecHandler(ErrorConfirmation e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}