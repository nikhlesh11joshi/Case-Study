package com.test.iris.product_service.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDto {

    @Schema(description = "Product ID", example = "1")
    @NotNull
    private Long productId;
    @Schema(description = "Product Name", example = "Laptop")
    @NotNull
    private String name;
    @NotNull
    @Size(max = 100)
    @Schema(description = "Product Description", example = "Laptop with 8GB RAM")
    private String description;
    @NotNull
    @Schema(description = "Product Price", example = "50000")
    private Double price;
    @NotNull
    @Schema(description = "Product Category", example = "Electronics")
    private String category;
    @NotNull
    @Schema(description = "Product Quantity", example = "10")
    private Long quantity;

    public ProductDto() {
    }

    public ProductDto(Long productId, String name, String description, Double price, String category, Long quantity) {
        this.productId = productId;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
