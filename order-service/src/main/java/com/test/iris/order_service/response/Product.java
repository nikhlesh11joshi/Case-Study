package com.test.iris.order_service.response;


import io.swagger.v3.oas.annotations.media.Schema;

public class Product {
   // @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Product ID", example = "1")
    private Long productId;
   // @NotBlank
    @Schema(description = "Product Name", example = "Laptop")
    private String name;
    //@Size(max = 100)
    @Schema(description = "Product Description", example = "Laptop with 8GB RAM")
    private String description;
    @Schema(description = "Product Price", example = "50000")
    private Double price;
    //@NotBlank
    @Schema(description = "Product Category", example = "Electronics")
    private String category;
    @Schema(description = "Product Quantity", example = "10")
    private Long quantity;

    public Product() {
    }

    public Product(Long productId, String name, String description, Double price, String category, Long quantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
