package com.test.iris.order_service.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrderProductResponse {

    // @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Product ID", example = "1")
    private Long productId;

    public OrderProductResponse() {

    }

    public OrderProductResponse(Long productId) {

        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "OrderProductResponse{" +
                "productId=" + productId +
                '}';
    }
}
