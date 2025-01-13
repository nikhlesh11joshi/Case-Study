package com.test.iris.order_service.response;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long userId;
    @Schema(description = "Unique identifier of the order", example = "1")
    private Long orderId;
    @Schema(description = "List of products in the cart", example = "Laptop")
    private List<Product> cardItems;
    @Schema(description = "Total amount of the order", example = "50000")
    private Double totalAmount;
    @Schema(description = "Order status", example = "Order Placed")
    private String status;
    @Schema(description = "Email notification status", example = "true")
    private boolean isEmailNotificationSent;
    @Schema(description = "Phone notification status", example = "true")
    private boolean isPhoneNotificationSent;
    @Schema(description = "Tracking ID of the order", example = "123456")
    private String  trackingId;
    @Schema(description = "Order creation date time", example = "Timestamp format")
    private LocalDateTime createdAt;
    @Schema(description = "Order updated date time", example = "Timestamp format")
    private LocalDateTime updatedAt;
    @Schema(description = "User response model")
    private Data data;

    public OrderResponse() {
    }

    public OrderResponse(Long userId, Long orderId, List<Product> cardItems, Double totalAmount, String status, boolean isEmailNotificationSent, boolean isPhoneNotificationSent, String trackingId, LocalDateTime createdAt, LocalDateTime updatedAt, Data data) {
        this.userId = userId;
        this.orderId = orderId;
        this.cardItems = cardItems;
        this.totalAmount = totalAmount;
        this.status = status;
        this.isEmailNotificationSent = isEmailNotificationSent;
        this.isPhoneNotificationSent = isPhoneNotificationSent;
        this.trackingId = trackingId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.data = data;
    }

    @Schema(description = "Unique identifier of the user", example = "1")
    public Long getUserId() {
        return userId;
    }

    @Schema(description = "Unique identifier of the user", example = "1")
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Schema(description = "List of products in the cart", example = "Laptop")
    public List<Product> getCardItems() {
        return cardItems;
    }

    @Schema(description = "List of products in the cart", example = "Laptop")
    public void setCardItems(List<Product> cardItems) {
        this.cardItems = cardItems;
    }
    @Schema(description = "Total amount of the order", example = "50000")
    public Double getTotalAmount() {
        return totalAmount;
    }
    @Schema(description = "Total amount of the order", example = "50000")
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    @Schema(description = "Order status", example = "Order Placed")
    public String getStatus() {
        return status;
    }
    @Schema(description = "Order status", example = "Order Placed")
    public void setStatus(String status) {
        this.status = status;
    }
    @Schema(description = "Email notification status", example = "true")
    public boolean isEmailNotificationSent() {
        return isEmailNotificationSent;
    }
    @Schema(description = "Email notification status", example = "true")
    public void setEmailNotificationSent(boolean emailNotificationSent) {
        isEmailNotificationSent = emailNotificationSent;
    }
    @Schema(description = "Phone notification status", example = "true")
    public boolean isPhoneNotificationSent() {
        return isPhoneNotificationSent;
    }
    @Schema(description = "Phone notification status", example = "true")
    public void setPhoneNotificationSent(boolean phoneNotificationSent) {
        isPhoneNotificationSent = phoneNotificationSent;
    }
    @Schema(description = "Tracking ID of the order", example = "123456")
    public String getTrackingId() {
        return trackingId;
    }
    @Schema(description = "Tracking ID of the order", example = "123456")
    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }
    @Schema(description = "Order creation date time", example = "Timestamp format")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    @Schema(description = "Order creation date time", example = "Timestamp format")
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    @Schema(description = "Order updated date time", example = "Timestamp format")
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    @Schema(description = "Order updated date time", example = "Timestamp format")
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    @Schema(description = "User response model")
    public Data getData() {
        return data;
    }
    @Schema(description = "User response model")
    public void setData(Data data) {
        this.data = data;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Schema(description = "User response model")
    @Override
    public String toString() {
        return "OrderResponse{" +
                "userId=" + userId +
                ", cardItems=" + cardItems +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", isEmailNotificationSent=" + isEmailNotificationSent +
                ", isPhoneNotificationSent=" + isPhoneNotificationSent +
                ", trackingId='" + trackingId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", data=" + data +
                '}';
    }
}
