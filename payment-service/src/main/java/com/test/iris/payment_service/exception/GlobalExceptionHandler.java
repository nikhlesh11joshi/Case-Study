package com.test.iris.payment_service.exception;


import com.test.iris.payment_service.response.ErrorResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    }*/

    @ExceptionHandler(InvalidCardException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(InvalidCardException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.BAD_REQUEST.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(PaymentUpdateException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(PaymentUpdateException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.BAD_REQUEST.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(PaymentNotFoundException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.NOT_FOUND.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /*@ExceptionHandler(UserNoFoundException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(UserNoFoundException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.NOT_FOUND.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CardAlreadyExistForUserException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(CardAlreadyExistForUserException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.CONFLICT.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    @ExceptionHandler(InvalidCardException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(InvalidCardException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.BAD_REQUEST.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }*/
}
