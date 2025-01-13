package com.test.iris.product_service.controller;


import com.test.iris.product_service.model.Product;
import com.test.iris.product_service.request.ProductDto;
import com.test.iris.product_service.response.NotificationResponse;
import com.test.iris.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Product Service",
        description = "APIs for product service")
@RestController
public class ProductClientController {


    private Logger logger = LoggerFactory.getLogger(ProductClientController.class);

    @Autowired
    private ProductService productService;

    @PutMapping("/update-product/status")
    void updateOrderStatus(@Valid @RequestBody List<ProductDto> cartItems, HttpServletRequest request, @RequestHeader("Authorization") String authorizationHeader) {
      //  String bearerToken = request.getHeader("Authorization");
        logger.info("Product status update request received for cart items: " + cartItems);
         productService.updateProductStatus(cartItems,authorizationHeader);
    }
}
