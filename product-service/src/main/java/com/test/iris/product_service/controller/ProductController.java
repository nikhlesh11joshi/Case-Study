package com.test.iris.product_service.controller;

import com.test.iris.product_service.request.ProductDto;
import com.test.iris.product_service.request.UpdateProductDto;
import com.test.iris.product_service.response.ProductAvailabilityListResponse;
import com.test.iris.product_service.response.ProductAvailabilityResponse;
import com.test.iris.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product Management",
        description = "APIs for managing product operations")
@RestController
@RequestMapping
public class ProductController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get user by username", description = "Fetch a user by their unique username")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product details to be added", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("add product Request recieved : "+ productDto);
        return productService.addProduct(productDto);
    }


    @Operation(summary = "Get product by name", description = "Fetch a product by their unique name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @Parameter(description = "Product name to be fetched", required = true)
    @GetMapping("/getProductByName/productName/{productName}")
    public ResponseEntity<?> getProductByName(@NotBlank @PathVariable(value = "productName") String productName) {
        logger.info("Get product by name Request recieved : "+ productName);
        return productService.getProductByName(productName);
    }


    @Operation(summary = "Get product by productId", description = "Fetch a product by their unique productId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @Parameter(description = "Product Id to be fetched", required = true)
    @GetMapping("/getProductByProductId/productId/{productId}")
    public ResponseEntity<?> getProductByProductId(@NotNull @PathVariable(value = "productId") Long productId) {
        logger.info("Get product by name Request recieved : "+ productId);
        return productService.getProductByProductId(productId);
    }


    @Operation(summary = "Get all products", description = "Fetch all products")
    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts() {
        logger.info("Get all products Request recieved");
        return productService.fetchAllProducts();
    }
    @Operation(summary = "Update product", description = "Update product details")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product details to be updated", required = true)
    @Parameter(description = "Product Id to be updated", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/updateProduct/productId/{productId}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody UpdateProductDto productDto, @NotNull @PathVariable(value = "productId") Long productId) {
        logger.info("update product Request recieved : "+ productDto);
        return productService.updateProduct(productDto,productId);
    }


    @Operation(summary = "Delete product", description = "Delete product by productId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @Parameter(description = "Product Id to be deleted", required = true)
    @DeleteMapping("/deleteProduct/productId/{productId}")
    public ResponseEntity<?> deleteProduct(@NotNull @PathVariable("productId") Long productId) {
        logger.info("delete product Request recieved : "+ productId);
        return productService.deleteProductById(productId);
    }


    @Operation(summary = "Delete all products", description = "Delete all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All products deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Products not found")
    })
    @Parameter(description = "Product Id to be deleted", required = true)
    @DeleteMapping("/deleteProductByName/productName/{productName}")
    public ResponseEntity<?> deleteProductByName(@NotNull @PathVariable(value = "productName")  String productName) {
        logger.info("delete all products Request recieved");
        return productService.deleteProductByname(productName);
    }


    @Operation(summary = "Fetch product by category", description = "Fetch product by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @Parameter(description = "Product category to be fetched", required = true)
    @GetMapping("/fetchProductByCategory/category/{category}")
    public ResponseEntity<?> fetchProductByCategory(@NotNull @PathVariable(value = "category") String category) {
        logger.info("fetch product by category Request recieved : "+ category);
        return productService.fetchAllProdcutsByCategory(category);
    }

    @Operation(summary = "Check product availability", description = "Check product availability")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product available"),
            @ApiResponse(responseCode = "404", description = "Product not available")
    })
    @GetMapping("/check-availability/productId/{productId}/quantity/{quantity}")
    public ResponseEntity<ProductAvailabilityResponse> checkProductAvailability(@NotNull @PathVariable Long productId,
                                                               @NotNull @PathVariable Long quantity) {
        logger.info("checkProductAvailability - Request recieved :  productId : "+productId +" quantity : "+quantity);
        // Logic to check product availability
       return  productService.checkProductAvailabilityandQty(productId,quantity);
    }
    @Operation(summary = "Check product availability", description ="This is an internal API Call from Order Service to check product availability")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product available"),
            @ApiResponse(responseCode = "404", description = "Product not available")
    })
    @PostMapping("/check-availability")
    public ResponseEntity<ProductAvailabilityListResponse> checkProductAvailabilityList(@Valid @RequestBody List<ProductDto> cartItems) {
        logger.info("checkProductAvailability - Request recieved :  cartItems : "+cartItems);
        // Logic to check product availability
        return  productService.checkProductAvailabilityandQtyList(cartItems);
    }


}
