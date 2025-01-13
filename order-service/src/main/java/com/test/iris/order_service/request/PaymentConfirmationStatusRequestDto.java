package com.test.iris.order_service.request;

import jakarta.validation.constraints.NotNull;

public class PaymentConfirmationStatusRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long orderId;
    @NotNull
    private String paymentStatus;


    public PaymentConfirmationStatusRequestDto() {
    }

    public PaymentConfirmationStatusRequestDto(Long userId, Long orderId, String paymentStatus) {
        this.userId = userId;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "PaymentConfirmationStatusDto{" +
                "userId=" + userId +
                ", orderId=" + orderId +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
