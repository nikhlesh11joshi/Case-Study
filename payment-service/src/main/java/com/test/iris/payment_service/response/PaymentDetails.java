package com.test.iris.payment_service.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class PaymentDetails {

    private Long orderId;

    private Long userId;

    private Double amount;

    private String paymentStatus;

    private String paymentMethod;

    private String paymentGateway;


    private String transactionId;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
