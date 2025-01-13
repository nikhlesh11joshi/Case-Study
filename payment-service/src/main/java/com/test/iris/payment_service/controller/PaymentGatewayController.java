package com.test.iris.payment_service.controller;

import com.test.iris.payment_service.request.PaymentRequestDto;
import com.test.iris.payment_service.response.PaymentResponseDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentGatewayController {

    private Logger logger = LoggerFactory.getLogger(PaymentGatewayController.class);

    @PostMapping("/gateway-process-payment")
    public ResponseEntity<PaymentResponseDto> gatewayrocessedPayment(@RequestBody  PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setPaymentStatus("SUCCESS");
        paymentResponseDto.setAmount(paymentRequestDto.getAmount());
        paymentResponseDto.setOrderId(paymentRequestDto.getOrderId());
        paymentResponseDto.setUserId(paymentRequestDto.getUserId());
        paymentResponseDto.setTransactionId("TRXN" + System.currentTimeMillis());
        paymentResponseDto.setStatusCode(200);
        paymentResponseDto.setOrderId(paymentRequestDto.getOrderId());
        logger.info("Payment processed successfully for order id: {}", paymentRequestDto.getOrderId());
        return ResponseEntity.ok(paymentResponseDto);
    }



}
