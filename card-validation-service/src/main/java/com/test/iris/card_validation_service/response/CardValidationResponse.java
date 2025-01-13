package com.test.iris.card_validation_service.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class CardValidationResponse {
    @Schema(description = "Card validation status", example = "true")
    private boolean isValid;
    @Schema(description = "Validation message", example = "Card is valid")
    private String message;

    public CardValidationResponse() {
    }

    public CardValidationResponse(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CardValidationResponse{" +
                "isValid=" + isValid +
                ", message='" + message + '\'' +
                '}';
    }
}
