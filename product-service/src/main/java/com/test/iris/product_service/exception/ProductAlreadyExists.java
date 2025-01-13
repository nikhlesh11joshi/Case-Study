package com.test.iris.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductAlreadyExists extends RuntimeException {
    public ProductAlreadyExists(String message) {
        super(message);
    }
}
