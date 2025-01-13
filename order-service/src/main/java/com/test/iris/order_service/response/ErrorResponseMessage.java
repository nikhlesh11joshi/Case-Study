package com.test.iris.order_service.response;


import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorResponseMessage {
    @Schema(description = "Error status", example = "404")
    private String status;
    @Schema(description = "Error message", example = "Product not found")
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
