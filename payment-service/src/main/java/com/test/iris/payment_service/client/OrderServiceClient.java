package com.test.iris.payment_service.client;

import com.test.iris.payment_service.request.PaymentConfirmationStatusRequestDto;
import com.test.iris.payment_service.response.PaymentConfirmationStatusResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderServiceClient", url = "http://localhost:8085/orders")
public interface OrderServiceClient {
    @PutMapping("/update-order/status")
    ResponseEntity<PaymentConfirmationStatusResponseDto> sendPaymentConfirmationNotificationToOrderService(@Valid @RequestBody PaymentConfirmationStatusRequestDto paymentConfirmationStatusDto);
}
