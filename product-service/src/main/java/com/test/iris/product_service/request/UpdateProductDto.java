package com.test.iris.product_service.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateProductDto {
    @Schema(description = "Product Name", example = "Laptop")
    @JsonProperty(required = false)
    private String name;
    @JsonProperty(required = false)
    @Size(max = 100)
    @Schema(description = "Product Description", example = "Laptop with 8GB RAM")
    private String description;
    @JsonProperty(required = false)
    @Schema(description = "Product Price", example = "50000")
    private Double price;
    @JsonProperty(required = false)
    @Schema(description = "Product Category", example = "Electronics")
    private String category;
    @JsonProperty(required = false)
    @Schema(description = "Product Quantity", example = "10")
    private Long quantity;

    public void updateProductDto() {
    }

    public void updateProductDto(String name, String description, Double price, String category, Long quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "updateProductDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
