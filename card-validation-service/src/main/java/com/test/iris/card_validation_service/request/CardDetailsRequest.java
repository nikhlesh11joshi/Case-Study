package com.test.iris.card_validation_service.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CardDetailsRequest {

    @NotNull(message = "Card number can't be blank or null.")
    @Pattern(regexp = "\\d{13,19}", message = "Card number must be between 13 and 19 digits.")
    @Schema(description = "Card number", example = "1234567890123456")
    private String cardNumber;

    @Schema(description = "Card holder name", example = "John Doe")
    @NotNull(message = "Card holder name can't be blank or null.")
    private String cardHolderName;

    @Schema(description = "Card type", example = "VISA")
    @NotNull(message = "Card type cannot be blank.")
    @Pattern(regexp = "VISA|MASTERCARD|AMEX|DISCOVER", message = "Card type must be one of VISA, MASTERCARD, AMEX, or DISCOVER.")
    private String cardType;

    @Schema(description = "Expiration month", example = "01")
    @Pattern(regexp = "^(0[1-9]|1[0-2])$", message = "Expiration month must be in MM format and between 01 and 12.")
    private  String expirationMonth;

    @Schema(description = "Expiration year", example = "2023")
    @Pattern(regexp = "^[0-9]{4}$", message = "Expiration year must be in YYYY format.")
    private String expirationYear;
    @Schema(description = "CVV", example = "123")
    @NotNull(message = "CVV can't be blank or null.")
    @Size(min = 3, max = 4, message = "CVV must be between 3 and 4 digits.")
    @Pattern(regexp = "\\d{3,4}", message = "CVV must be between 3 and 4 digits.")
    private String cvv;

    private Long userId;

    public CardDetailsRequest() {
    }

    public CardDetailsRequest(String cardNumber, String cardHolderName, String cardType, String expirationMonth, String expirationYear, String cvv, Long userId) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardType = cardType;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "CardDetailsRequst{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", cardType='" + cardType + '\'' +
                ", expirationMonth='" + expirationMonth + '\'' +
                ", expirationYear='" + expirationYear + '\'' +
                ", cvv='" + cvv + '\'' +
                ", userId=" + userId +
                '}';
    }
}
