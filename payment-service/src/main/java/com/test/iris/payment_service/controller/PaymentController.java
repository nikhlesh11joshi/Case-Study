package com.test.iris.payment_service.controller;

import com.test.iris.payment_service.request.PaymentRequestDto;
import com.test.iris.payment_service.response.PaymentResponseDto;
import com.test.iris.payment_service.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment Service",
        description = "APIs for order payment")
@RestController
public class PaymentController {

    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private  PaymentService paymentService;

    @Operation(summary = "Create Payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment created"),
            @ApiResponse(responseCode = "404", description = "Payment not created")
    })
    @PostMapping("/createPayment")
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        logger.info("Payment request received: " + paymentRequestDto);
        ResponseEntity<PaymentResponseDto> paymentResponse=   paymentService.createPayment(paymentRequestDto);
        return ResponseEntity.ok(paymentResponse.getBody());
    }

    @Operation(summary = "Get Payment details  by PaymentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment found"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/getPaymentById/paymentId/{paymentId}/userId/{userId}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@NotNull @PathVariable("paymentId") Long paymentId, @NotNull @PathVariable("userId") Long userId) {
        logger.info("Get payment by paymentId: " + paymentId);
        return paymentService.getPaymentByPaymentId(paymentId, userId);
    }


    @Operation(summary = "Update Payment by PaymentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/updatePayment/{paymentId}")
    public ResponseEntity<PaymentResponseDto> updatePayment(@NotNull @PathVariable Long paymentId,@Valid  @RequestBody PaymentRequestDto paymentRequestDto) {
        logger.info("Update payment by paymentId: " + paymentId + " with request: " + paymentRequestDto);
        return paymentService.updatePaymentByPaymentId(paymentId, paymentRequestDto);
    }
/*
    @DeleteMapping("/deletePayment/{paymentId}")
    public ResponseEntity<PaymentResponseDto> deletePayment(Long paymentId) {
        return ResponseEntity.ok(new PaymentResponseDto("Payment deleted successfully"));
    }*/

}
