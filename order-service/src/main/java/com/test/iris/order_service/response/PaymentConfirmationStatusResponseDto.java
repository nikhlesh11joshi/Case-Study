package com.test.iris.order_service.response;

import jakarta.validation.constraints.NotNull;

public class PaymentConfirmationStatusResponseDto {
    @NotNull
    private String message;
    @NotNull
    private String status;

    public PaymentConfirmationStatusResponseDto() {
    }

    public PaymentConfirmationStatusResponseDto(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaymentConfirmationStatusResponseDto{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
