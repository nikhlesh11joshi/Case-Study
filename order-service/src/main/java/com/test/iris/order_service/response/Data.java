package com.test.iris.order_service.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.net.URL;

public class Data {
    @Schema(description = "Message", example = "Payment is successful")
    private String message;
    @Schema(description = "Payment URL", example = "http://localhost:8080/payment")
    private String paymentUrl;

    public Data() {

    }

    public Data(String message, String paymentUrl) {
        this.message = message;
        this.paymentUrl = paymentUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String  getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    @Override
    public String toString() {
        return "Data{" +
                "message='" + message + '\'' +
                ", paymentUrl=" + paymentUrl +
                '}';
    }
}
