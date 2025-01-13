package com.test.iris.order_service.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductAvailabilityResponse {
    @Schema(description = "Product availability status", example = "true")
    private boolean available;
    @Schema(description = "Product availability message", example = "Product is available")
    private String message;

    public ProductAvailabilityResponse() {
    }

    public ProductAvailabilityResponse(boolean available, String message) {
        this.available = available;
        this.message = message;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ProductAvailabilityResponse{" +
                "available=" + available +
                ", message='" + message + '\'' +
                '}';
    }
}
