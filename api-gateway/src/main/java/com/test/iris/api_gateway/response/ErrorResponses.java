package com.test.iris.api_gateway.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class ErrorResponses {
    @Schema(description = "Status", example = "error")
    private int status;
    @Schema(description = "Error message", example = "Card number must be between 13 and 19 digits.")
    private String error;
    @Schema(description = "Error message", example = "Card number must be between 13 and 19 digits.")
    private String message;
    @Schema(description = "Error message", example = "Card number must be between 13 and 19 digits.")
    private LocalDateTime timestamp;

    public ErrorResponses(int status, String error, String message, LocalDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
