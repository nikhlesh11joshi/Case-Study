package com.test.iris.card_validation_service.exception;

public class InvalidCardException extends RuntimeException  {
    public InvalidCardException(String message) {
        super(message);
    }
}
