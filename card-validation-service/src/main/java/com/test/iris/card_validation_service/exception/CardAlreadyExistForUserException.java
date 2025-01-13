package com.test.iris.card_validation_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CardAlreadyExistForUserException extends RuntimeException {
    public CardAlreadyExistForUserException(String message) {
        super(message);
    }
}
