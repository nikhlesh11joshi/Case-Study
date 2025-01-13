package com.test.iris.order_service.response;


import com.test.iris.order_service.model.OrderProduct;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseById {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long userId;
    @Schema(description = "List of products in the cart", example = "Laptop")
    private List<OrderProduct> cardItems;
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
    /*@Schema(description = "User response model")
    private Data data;*/

    public OrderResponseById() {
    }

    public OrderResponseById(Long userId, List<OrderProduct> cardItems, Double totalAmount, String status, boolean isEmailNotificationSent, boolean isPhoneNotificationSent, String trackingId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.cardItems = cardItems;
        this.totalAmount = totalAmount;
        this.status = status;
        this.isEmailNotificationSent = isEmailNotificationSent;
        this.isPhoneNotificationSent = isPhoneNotificationSent;
        this.trackingId = trackingId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
    public List<OrderProduct> getCardItems() {
        return cardItems;
    }

    @Schema(description = "List of products in the cart", example = "Laptop")
    public void setCardItems(List<OrderProduct> cardItems) {
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


    @Override
    public String toString() {
        return "OrderResponseById{" +
                "userId=" + userId +
                ", cardItems=" + cardItems +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", isEmailNotificationSent=" + isEmailNotificationSent +
                ", isPhoneNotificationSent=" + isPhoneNotificationSent +
                ", trackingId='" + trackingId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
