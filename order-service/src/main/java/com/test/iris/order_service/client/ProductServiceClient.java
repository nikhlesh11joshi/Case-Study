package com.test.iris.order_service.client;

import com.test.iris.order_service.response.Product;
import com.test.iris.order_service.response.ProductAvailabilityListResponse;
import com.test.iris.order_service.response.ProductAvailabilityResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8081/products")
public interface ProductServiceClient {
    @GetMapping("/check-availability/productId/{productId}/quantity/{quantity}")
    ProductAvailabilityResponse checkProductAvailability(@PathVariable("productId") Long productId,
                                                         @RequestParam("quantity") Long quantity,
                                                         @RequestHeader("Authorization") String authorizationHeader);

    @PostMapping("/check-availability")
    ProductAvailabilityListResponse checkProductAvailabilityList(@RequestBody List<Product> cartItems,
                                                             @RequestHeader("Authorization") String authorizationHeader);

    @PutMapping("/update-product/status")
    void updateOrderStatus(@RequestBody List<Product> cartItems,@RequestHeader("Authorization") String authorizationHeader);





}
