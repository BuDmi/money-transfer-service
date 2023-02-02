package ru.netology.moneytransfer.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransfer.exception.ErrorConfirmation;
import ru.netology.moneytransfer.exception.ErrorInputData;
import ru.netology.moneytransfer.exception.ErrorTransfer;
import ru.netology.moneytransfer.model.ErrorResponse;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<ErrorResponse> eidHandler(ErrorInputData e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), httpStatus.value()), httpStatus);
    }

    @ExceptionHandler(ErrorTransfer.class)
    public ResponseEntity<ErrorResponse> etHandler(ErrorTransfer e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), httpStatus.value()), httpStatus);
    }

    @ExceptionHandler(ErrorConfirmation.class)
    public ResponseEntity<ErrorResponse> ecHandler(ErrorConfirmation e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), httpStatus.value()), httpStatus);
    }
}