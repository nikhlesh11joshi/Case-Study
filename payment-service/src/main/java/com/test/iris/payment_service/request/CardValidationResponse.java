package com.test.iris.payment_service.request;

public class CardValidationResponse {
    private boolean isValid;
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
