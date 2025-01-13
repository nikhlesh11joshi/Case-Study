package com.test.iris.card_validation_service.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class CardDetailsResponse {

    @Schema(description = "Card number", example = "1234567890123456")
    private String cardNumber;

   @Schema(description = "Card holder name", example = "John Doe")
    private String cardHolderName;

    @Schema(description = "Card type", example = "VISA")
   private String cardType;

    @Schema(description = "Expiration month", example = "01")
    private  String expirationMonth;
    @Schema(description = "Expiration year", example = "2023")
    private String expirationYear;

    @Schema(description = "CVV", example = "123")
    private String cvv;
    @Schema(description = "User Id", example = "1")
    private Long userId;

    public CardDetailsResponse() {
    }

    public CardDetailsResponse(String cardNumber, String cardHolderName, String cardType, String expirationMonth, String expirationYear, String cvv, Long userId) {
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
