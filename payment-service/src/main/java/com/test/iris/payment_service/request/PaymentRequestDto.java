package com.test.iris.payment_service.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

public class PaymentRequestDto {
    //@Column(name = "order_id")
    @NotNull
    private Long orderId;
    //@Column(name = "user_id")
    @NotNull
    private Long userId;
    // @Column(name = "amount")
    @NotNull
    private Double amount;

    private String paymentGateway;

    private String paymentMethod;
    @JsonProperty(required = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    //@Column(name = "updated_at", nullable = false)
    @JsonProperty(required = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    private CardDetailsRequest cardDetailsRequest;

    public PaymentRequestDto() {
    }

    public PaymentRequestDto(Long orderId, Long userId, Double amount, String paymentGateway, String paymentMethod, LocalDateTime createdAt, LocalDateTime updatedAt, CardDetailsRequest cardDetailsRequest) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.paymentGateway = paymentGateway;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.cardDetailsRequest = cardDetailsRequest;
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

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CardDetailsRequest getCardDetailsRequest() {
        return cardDetailsRequest;
    }

    public void setCardDetailsRequest(CardDetailsRequest cardDetailsRequest) {
        this.cardDetailsRequest = cardDetailsRequest;
    }

    @Override
    public String toString() {
        return "PaymentRequestDto{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", paymentGateway='" + paymentGateway + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", cardDetailsRequest=" + cardDetailsRequest +
                '}';
    }
}


