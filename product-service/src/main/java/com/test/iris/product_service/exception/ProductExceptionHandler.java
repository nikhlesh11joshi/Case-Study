package com.test.iris.product_service.exception;



import com.test.iris.product_service.response.ErrorResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ProductExceptionHandler
{
    /*@ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorResponses> handleUnauthorizedUserException(UnauthorizedUserException ex) {
        ErrorResponses errorResponse = new ErrorResponses(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized Access",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponses> productNotFoundException(ProductNotFoundException ex) {
        com.test.iris.product_service.response.ErrorResponses errorResponse = new com.test.iris.product_service.response.ErrorResponses(ex.getMessage(),HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<ErrorResponses> ProductOutOfStockException(ProductOutOfStockException ex) {
        com.test.iris.product_service.response.ErrorResponses errorResponse = new com.test.iris.product_service.response.ErrorResponses(ex.getMessage(),HttpStatus.GONE.value());
        return ResponseEntity.status(HttpStatus.GONE).body(errorResponse);
    }

    @ExceptionHandler(ProductAlreadyExists.class)
    public ResponseEntity<ErrorResponses> ProductOutOfStockException(ProductAlreadyExists ex) {
        com.test.iris.product_service.response.ErrorResponses errorResponse = new com.test.iris.product_service.response.ErrorResponses(ex.getMessage(),HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /*@ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponseMessage> habdleTokenExpirationException(TokenExpiredException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.UNAUTHORIZED.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }*/
}
