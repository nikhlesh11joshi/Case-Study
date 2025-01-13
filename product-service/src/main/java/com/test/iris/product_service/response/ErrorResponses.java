package com.test.iris.product_service.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorResponses {
    @Schema(description = "Error message", example = "Product not found")
    private String message;
    @Schema(description = "Error status", example = "404")
    private int status;

    public ErrorResponses() {
    }

    public ErrorResponses(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
