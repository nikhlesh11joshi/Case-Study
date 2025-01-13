package com.test.iris.payment_service.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CardDetailsRequest {

    @NotNull(message = "Card number can't be blank or null.")
    @Pattern(regexp = "\\d{13,19}", message = "Card number must be between 13 and 19 digits.")
    private String cardNumber;

    @NotNull(message = "Card holder name can't be blank or null.")
    private String cardHolderName;

    @NotNull(message = "Card type cannot be blank.")
    @Pattern(regexp = "VISA|MASTERCARD|AMEX|DISCOVER", message = "Card type must be one of VISA, MASTERCARD, AMEX, or DISCOVER.")
    private String cardType;

    @Pattern(regexp = "^(0[1-9]|1[0-2])$", message = "Expiration month must be in MM format and between 01 and 12.")
    private  String expirationMonth;

    @Pattern(regexp = "^[0-9]{4}$", message = "Expiration year must be in YYYY format.")
    private String expirationYear;

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
