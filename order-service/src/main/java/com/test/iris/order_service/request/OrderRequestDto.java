package com.test.iris.order_service.request;

import com.test.iris.order_service.response.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderRequestDto {
    @Schema(description = "User ID", example = "1")
    @NotNull
    private Long userId;
    @NotNull
    @Schema(description = "List of products in cart. Support only one item in cart")
    private List<Product> cartItems;
    @Schema(description = "Note for the order", example = "Please deliver the product by 5 PM")
    private String note;
    @NotNull
    @Schema(description = "Payment method", example = "UPI")
    private String paymentMethod;

    public OrderRequestDto() {
    }

    public OrderRequestDto(Long userId, List<Product> cartItems, String note, String paymentMethod) {
        this.userId = userId;
        this.cartItems = cartItems;
        this.note = note;
        this.paymentMethod = paymentMethod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Product> cartItems) {
        this.cartItems = cartItems;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "OrderRequestDto{" +
                "userId=" + userId +
                ", cartItems=" + cartItems +
                ", note='" + note + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
