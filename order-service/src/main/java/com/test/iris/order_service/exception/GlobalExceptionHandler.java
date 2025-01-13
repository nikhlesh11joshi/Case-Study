package com.test.iris.order_service.exception;


import com.test.iris.order_service.response.ErrorResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CardValidationResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        CardValidationResponse cardValidationResponse = new CardValidationResponse(false, "Validation Error: " + errorMessage);
        return ResponseEntity.badRequest().body(cardValidationResponse);
    }

    @ExceptionHandler(NoCardFoundException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(NoCardFoundException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.NOT_FOUND.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserNoFoundException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(UserNoFoundException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.NOT_FOUND.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CardAlreadyExistForUserException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(CardAlreadyExistForUserException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.CONFLICT.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }*/

    @ExceptionHandler(NoOrderFoundException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(NoOrderFoundException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.BAD_REQUEST.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(UserNoFoundException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(UserNoFoundException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.NOT_FOUND.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(ProductNotFoundException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.NOT_FOUND.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
