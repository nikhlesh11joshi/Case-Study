package com.test.iris.card_validation_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoCardFoundException extends RuntimeException {
    public NoCardFoundException(String message) {
        super(message);
    }
}
