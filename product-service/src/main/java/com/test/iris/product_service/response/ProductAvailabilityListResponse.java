package com.test.iris.product_service.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ProductAvailabilityListResponse {
    @Schema(description = "Product availability status", example = "true")
    private boolean available;
    @Schema(description = "Product availability message", example = "Product is available")
    private String message;

    private List<ProductResponse> products;

    public ProductAvailabilityListResponse() {
    }

    public ProductAvailabilityListResponse(boolean available, String message, List<ProductResponse> products) {
        this.available = available;
        this.message = message;
        this.products = products;
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

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductAvailabilityListResponse{" +
                "available=" + available +
                ", message='" + message + '\'' +
                ", products=" + products +
                '}';
    }
}
