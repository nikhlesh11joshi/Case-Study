package com.test.iris.card_validation_service.response;


import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorResponseMessage {
    @Schema(description = "Status", example = "error")
    private String status;
    @Schema(description = "Error message", example = "Card number must be between 13 and 19 digits.")
    private String message;

    public ErrorResponseMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponseMessage{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
