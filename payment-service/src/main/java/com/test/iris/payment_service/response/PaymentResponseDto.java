package com.test.iris.payment_service.response;

public class PaymentResponseDto {
    private Long orderId;
    private Long userId;
    private Double amount;
    private String transactionId;
    private String paymentStatus;
    private int statusCode;

    public PaymentResponseDto() {
    }

    public PaymentResponseDto(Long orderId, Long userId, Double amount, String transactionId, String paymentStatus, int statusCode) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
        this.statusCode = statusCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "PaymentResponseDto{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", transactionId='" + transactionId + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", statusCode=" + statusCode +
                '}';
    }
}
