package com.test.iris.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class ProductOutOfStockException extends RuntimeException {

    public ProductOutOfStockException(String message) {
        super(message);
    }
}
