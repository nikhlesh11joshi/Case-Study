package com.test.iris.card_validation_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Cards {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Card number can't be blank or null.")
    @Pattern(regexp = "\\d{13,19}", message = "Card number must be between 13 and 19 digits.")
    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "card_holder_name", nullable = false)
    @NotNull(message = "Card holder name can't be blank or null.")
    private String cardHolderName;

    @Column(name = "card_type", nullable = false)
    @NotNull(message = "Card type cannot be blank.")
    @Pattern(regexp = "VISA|MASTERCARD|AMEX|DISCOVER", message = "Card type must be one of VISA, MASTERCARD, AMEX, or DISCOVER.")
    private String cardType;

    @Column(name = "expiration_month", nullable = false)
    @Pattern(regexp = "^(0[1-9]|1[0-2])$", message = "Expiration month must be in MM format and between 01 and 12.")
    private  String expirationMonth;

    @Column(name = "expiration_year", nullable = false)
    @Pattern(regexp = "^[0-9]{4}$", message = "Expiration year must be in YYYY format.")
    private String expirationYear;

    @Column(name = "cvv", nullable = false)
    @NotNull(message = "CVV can't be blank or null.")
    @Size(min = 3, max = 4, message = "CVV must be between 3 and 4 digits.")
    @Pattern(regexp = "\\d{3,4}", message = "CVV must be between 3 and 4 digits.")
    private String cvv;

    @Column(name = "user_id")
    @NotNull(message = "User id can't be blank or null.")
    private Long userId;

    @NotNull
    @Column(name = "created_at", nullable = false,updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Cards() {
    }

    public Cards(String cardNumber, String cardHolderName, String cardType, String expirationMonth, String expirationYear, String cvv, Long userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardType = cardType;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Cards{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", cardType='" + cardType + '\'' +
                ", expirationMonth='" + expirationMonth + '\'' +
                ", expirationYear='" + expirationYear + '\'' +
                ", cvv='" + cvv + '\'' +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
