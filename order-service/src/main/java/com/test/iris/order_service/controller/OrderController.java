package com.test.iris.order_service.controller;




import com.test.iris.order_service.request.OrderRequestDto;
import com.test.iris.order_service.response.OrderResponse;
import com.test.iris.order_service.response.OrderResponseById;
import com.test.iris.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Order Management",
        description = "APIs for managing order operations")
@RestController
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Create Order", description = "Create a new order")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order details to be added", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created"),
            @ApiResponse(responseCode = "404", description = "Order not created")
    })
    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@Valid  @RequestBody OrderRequestDto orderRequestDto, HttpServletRequest request) {
            logger.info("order request Recieved: "+ orderRequestDto);
        String bearerToken = request.getHeader("Authorization");

            ResponseEntity<OrderResponse> response =orderService.createOrders(orderRequestDto,bearerToken);
        return ResponseEntity.ok(response.getBody());
    }
    @Operation(summary = "Get order by orderId", description = "Fetch a order by their unique orderId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<OrderResponseById> getOrderByOrderId(@PathVariable Long orderId) {
        logger.info("Get order by orderId Request recieved : "+ orderId);
        OrderResponseById order = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.ok(order);
    }

    /*  @Operation(summary = "Update order by orderId", description = "Update a order by their unique orderId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated"),
            @ApiResponse(responseCode = "404", description = "Order not updated")
    })
  @PutMapping("/orderId{orderId}")
    public ResponseEntity<OrderResponse> updateOrderByOrderId(@NotNull @PathVariable Long orderId, @Valid @RequestBody OrderRequestDto orderRequestDto) {
        logger.info("Update order by orderId Request recieved : "+ orderId);
       // OrderResponse updatedOrder = orderService.updateOrderByOrderId(orderId, orderRequestDto);
        return ResponseEntity.ok(updatedOrder);
    }*/


    @Operation(summary = "Delete order by orderId", description = "Delete a order by their unique orderId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted"),
            @ApiResponse(responseCode = "404", description = "Order not deleted")
    })
    @DeleteMapping("/orderId/{orderId}")
    public ResponseEntity<OrderResponse> deleteOrderByOrderId(@NotNull @PathVariable Long orderId) {
        logger.info("Delete order by orderId Request recieved : "+ orderId);
        OrderResponse deletedOrder = orderService.deleteOrderByOrderId(orderId);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Get all orders by userId", description = "Fetch all orders by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found"),
            @ApiResponse(responseCode = "404", description = "Orders not found")
    })
    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<OrderResponseById>> getAllOrdersByUserId( @NotNull  @PathVariable("userId") Long userId) {
    logger.info("Get all orders by userId Request recieved : "+ userId);
        List<OrderResponseById> orders = orderService.getAllOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

}
