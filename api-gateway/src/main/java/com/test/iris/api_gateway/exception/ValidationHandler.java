package com.test.iris.api_gateway.exception;

import com.test.iris.api_gateway.response.ErrorResponseMessage;
import com.test.iris.api_gateway.response.ErrorResponses;
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
public class ValidationHandler
{
    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorResponses> handleUnauthorizedUserException(UnauthorizedUserException ex) {
        ErrorResponses errorResponse = new ErrorResponses(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized Access",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

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

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateUserException(InvalidTokenException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.UNAUTHORIZED.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    /*@ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponseMessage> habdleTokenExpirationException(TokenExpiredException ex) {
        ErrorResponseMessage errorResponse = new ErrorResponseMessage(HttpStatus.UNAUTHORIZED.toString(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }*/
}
