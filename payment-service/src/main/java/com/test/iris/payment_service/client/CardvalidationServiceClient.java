package com.test.iris.payment_service.client;

import com.test.iris.payment_service.request.CardDetailsRequest;
import com.test.iris.payment_service.request.CardValidationResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cardValidationServiceClient", url = "http://localhost:8087/cards/")
public interface CardvalidationServiceClient {

    @PostMapping("/validate-card")
    public ResponseEntity<CardValidationResponse> validateCard(@Valid @RequestBody CardDetailsRequest cardDetailsRequst);

}
