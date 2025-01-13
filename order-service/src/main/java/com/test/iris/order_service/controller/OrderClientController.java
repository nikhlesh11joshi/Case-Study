package com.test.iris.order_service.controller;

import com.test.iris.order_service.repository.OrderRepository;
import com.test.iris.order_service.request.PaymentConfirmationStatusRequestDto;
import com.test.iris.order_service.response.PaymentConfirmationStatusResponseDto;
import com.test.iris.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order Service",
        description = "APIs for order client service- To be used to update order status based on payment status")
@RestController
public class OrderClientController {

    private Logger logger = LoggerFactory.getLogger(OrderClientController.class);

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Update Order Status", description = "Update order status based on payment status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order status not updated")
    })
    @PutMapping("/update-order/status")
    public ResponseEntity<PaymentConfirmationStatusResponseDto> sendOrderConfirmationNotification(@Valid @RequestBody PaymentConfirmationStatusRequestDto paymentConfirmationStatusRequestDto, HttpServletRequest request) {
        logger.info("Order status update request received for order id: " + paymentConfirmationStatusRequestDto);
            String bearerToken = request.getHeader("Authorization");
        Long orderId = paymentConfirmationStatusRequestDto.getOrderId();
        Long userId = paymentConfirmationStatusRequestDto.getUserId();
        String paymentStatus = paymentConfirmationStatusRequestDto.getPaymentStatus();
        PaymentConfirmationStatusResponseDto paymentConfirmationStatusResponseDto = new PaymentConfirmationStatusResponseDto();
        if(paymentStatus.equals("SUCCESS")) {
            logger.info("Payment status is success for order id: {}", orderId);
            orderService.updateOrderStatus(orderId, userId, paymentStatus,"SUCCESS",bearerToken);
            paymentConfirmationStatusResponseDto.setStatus("SUCCESS");
            paymentConfirmationStatusResponseDto.setMessage("Order status updated successfully");
        } else if (paymentStatus.equals("FAILURE")) {
            logger.info("Payment status is failure for order id: {}", orderId);
            orderService.updateOrderStatus(orderId, userId, paymentStatus,"FAILURE",bearerToken);
            paymentConfirmationStatusResponseDto.setStatus("FAILURE");
            paymentConfirmationStatusResponseDto.setMessage("Order status FAILED");
        }
        else if (paymentStatus.equals("TIMEOUT")) {
            logger.info("Payment status is pending for order id: {}", orderId);
            orderService.updateOrderStatus(orderId, userId, paymentStatus,"TIMEOUT",bearerToken);
            paymentConfirmationStatusResponseDto.setStatus("TIMEOUT");
            paymentConfirmationStatusResponseDto.setMessage("Order status TIMEOUT");
        }
        else {
            logger.error("Invalid payment status for order id: {}", orderId);
            throw new RuntimeException("Invalid payment status");
        }

        paymentConfirmationStatusResponseDto.setMessage("Order status updated successfully");
        logger.info("Order status updated successfully for order id: {}", orderId + " with status: " + paymentStatus);
        return ResponseEntity.ok(paymentConfirmationStatusResponseDto);

    }
}
