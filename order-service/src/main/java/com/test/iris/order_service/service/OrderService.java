package com.test.iris.order_service.service;


import com.test.iris.order_service.client.ProductServiceClient;
import com.test.iris.order_service.exception.NoOrderFoundException;
import com.test.iris.order_service.exception.ProductNotFoundException;
import com.test.iris.order_service.exception.UserNoFoundException;
import com.test.iris.order_service.model.Order;
import com.test.iris.order_service.model.OrderProduct;
import com.test.iris.order_service.repository.OrderProductRepository;
import com.test.iris.order_service.repository.OrderRepository;
import com.test.iris.order_service.request.OrderProductDto;
import com.test.iris.order_service.request.OrderRequestDto;
import com.test.iris.order_service.response.*;
import com.test.iris.order_service.util.OrderConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${payment.url}")
    private String  paymentUrl;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private  ProductServiceClient productServiceClient;

    private static Map<Long, List<Product>> orderProductMap = new HashMap<>();

    public ResponseEntity<OrderResponse> createOrders(OrderRequestDto orderRequestDto,String bearerToken) {
        logger.info("order request Recieved: "+ orderRequestDto);
        Order savedOrder = null;
        Data data = new Data();
        OrderResponse orderResponse = null;
        Long userId = orderRequestDto.getUserId();
        List<Product> cartItems = orderRequestDto.getCartItems();

        if(userId !=null && cartItems.size()>0){
            Order newOrder = new Order();
            newOrder.setUserId(userId);
            newOrder.setCreatedAt(LocalDateTime.now());
            newOrder.setUpdatedAt(LocalDateTime.now());
            newOrder.setPaymentMethod(orderRequestDto.getPaymentMethod());
            newOrder.setPaymentStatus(OrderConstant.PAYMENT_STATUS_PENDING);
            newOrder.setEmailNotificationSend(false);
            newOrder.setPhoneNotificationSend(false);
            newOrder.setNote(orderRequestDto.getNote());
            newOrder.setStatus(OrderConstant.ORDER_STATUS_PENDING);
          //  newOrder.setOrderProductList(modelMapper.map(cartItems, List.class));
            boolean isProductsAvailable = checkProductAvailabilityList(cartItems,bearerToken);
            if (isProductsAvailable){
                Double totalAmount = cartItems.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
                logger.info("Total amount for the order: "+ totalAmount);
                newOrder.setTotalAmount(totalAmount);
                String trackingId =  System.currentTimeMillis()+"";
                logger.info("Tracking Id generated for the order: "+ trackingId +" for user: "+ userId+ "with cartItems: "+ cartItems+ "with total amount: "+ totalAmount);
                newOrder.setTrackingId(trackingId);
                savedOrder = orderRepository.save(newOrder);
                logger.info("Order saved in DB :successfully: "+ savedOrder);
                orderProductMap.put(savedOrder.getOrderId(), cartItems);
                List<OrderProduct> orderProductList = cartItems.stream().map(product -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProductId(product.getProductId());
                    orderProduct.setOrder(newOrder);
                    orderProduct.getOrder().setOrderId(newOrder.getOrderId());
                    return orderProduct;
                }).toList();
                logger.info("orderProductList : "+ orderProductList);
                newOrder.setOrderProductList(orderProductList);
                orderResponse= processOrderwithPendingWhenProductIsAvailable(userId, cartItems, trackingId);
                if(savedOrder.getPaymentStatus().equals(OrderConstant.PAYMENT_STATUS_PENDING)){
                    logger.info(" if payment-status-pending -- Order saved in DB With PENDING :successfully: "+ savedOrder);
                    data.setMessage("Payment pending for the order!! Please click on the below link to make payment");
                    paymentUrl = paymentUrl.replace("{amount}",totalAmount.toString()).replace("{orderId}",savedOrder.getOrderId().toString()).replace("{userId}",userId.toString());
                    logger.info("Payment URL generated for the order: "+ paymentUrl);
                    data.setPaymentUrl(paymentUrl);
                    orderResponse.setData(data);
                    orderResponse.setOrderId(savedOrder.getOrderId());
                    logger.info("Order saved in DB With PENDING :successfully: "+ orderResponse);
                    return ResponseEntity.ok(orderResponse);

                }
                else if(savedOrder.getPaymentStatus().equals(OrderConstant.PAYMENT_STATUS_CONFIRMED)){
                    logger.info(" if payment-status-CONFIRMED  -- Order saved in DB With PAID :successfully: "+ savedOrder);
                    data.setMessage("Payment successful for the order!! Your order is confirmed !! You will recieve the notification soon");
                    data.setPaymentUrl(null);
                    orderResponse.setData(data);
                    logger.info("Order saved in DB With PAID :successfully: "+ orderResponse);
                    return ResponseEntity.ok(orderResponse);

                }
                else if(savedOrder.getPaymentStatus().equals(OrderConstant.PAYMENT_STATUS_FAILED)){
                    logger.info(" if payment-status-FAILED  -- Order saved in DB With FAILED :successfully: "+ savedOrder);
                    data.setMessage("Payment failed for the order!! Please retry for the payment after 30 mins!!");
                    data.setPaymentUrl(null);
                    orderResponse.setData(data);
                    logger.info("Order saved in DB With FAILED :successfully: "+ orderResponse);
                    return ResponseEntity.ok(orderResponse);

                }
                else if (savedOrder.getPaymentStatus().equals(OrderConstant.PAYMENT_STATUS_TIMEOUT)){
                    logger.info(" if payment-status-CANCELLED  -- Order saved in DB With CANCELLED :successfully: "+ savedOrder);
                    data.setMessage("Payment timeout for the order!! Please retry the order Once again");
                    data.setPaymentUrl(null);
                    orderResponse.setData(data);
                    logger.info("Order saved in DB With CANCELLED :successfully: "+ orderResponse);

                    return ResponseEntity.ok(orderResponse);

                }
                else {
                    logger.info("Order saved in DB With PENDING :successfully: "+ savedOrder);
                    data.setMessage("order errror!! Please retry the order Once again");
                    data.setPaymentUrl(null);
                    orderResponse.setData(data);
                    //logger.info("Order saved in DB With PENDING :successfully: "+ orderResponse);
                    return ResponseEntity.ok(orderResponse);

                }


            }
            else {
                throw new RuntimeException("Product is not available for the order");
            }


        }
        throw new RuntimeException("userId or cartItems are invalid");

    }

    /*public ResponseEntity<OrderResponse> createOrder(OrderRequestDto orderRequestDto,String bearerToken) {
        logger.info("order request Recieved: "+ orderRequestDto);
        Long userId = orderRequestDto.getUserId();
        List<OrderProductDto> cartItems = orderRequestDto.getCartItems();
        Order newOrder = new Order();
        newOrder.setUserId(userId);
        newOrder.setCreatedAt(LocalDateTime.now());
        newOrder.setUpdatedAt(LocalDateTime.now());
        newOrder.setPaymentMethod(orderRequestDto.getPaymentMethod());
        newOrder.setPaymentStatus(OrderConstant.PAYMENT_STATUS_PENDING);
        newOrder.setEmailNotificationSend(false);
        newOrder.setPhoneNotificationSend(false);
        newOrder.setNote(orderRequestDto.getNote());

        Double totalAmount = cartItems.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
        newOrder.setTotalAmount(totalAmount);
        String trackingId =  System.currentTimeMillis()+"";
        logger.info("Tracking Id generated for the order: "+ trackingId +" for user: "+ userId+ "with cartItems: "+ cartItems+ "with total amount: "+ totalAmount);
        newOrder.setTrackingId(trackingId);
        newOrder.setProductIdsList(modelMapper.map(cartItems, List.class));
        Order savedOrder = null;
        logger.info("Order saved in DB :successfully: "+ savedOrder);

        OrderResponse orderResponse = null;
        Data data = new Data();
        boolean isProductAvailable = checkProductAvailability(cartItems.get(0).getProductId(), cartItems.get(0).getQuantity(),bearerToken);
        if(isProductAvailable){
            logger.info("Product is available for the order: "+ cartItems.get(0).getProductId());
            newOrder.setStatus(OrderConstant.ORDER_STATUS_PENDING);
            orderResponse= processOrderwithPendingWhenProductIsAvailable(userId, cartItems, trackingId);
           savedOrder= orderRepository.save(newOrder);
            if(savedOrder.getPaymentStatus().equals(OrderConstant.PAYMENT_STATUS_PENDING)){
                logger.info(" if payment-status-pending -- Order saved in DB With PENDING :successfully: "+ savedOrder);
                data.setMessage("Payment pending for the order!! Please click on the below link to make payment");
                data.setPaymentUrl(paymentUrl);
                logger.info("Order saved in DB With PENDING :successfully: "+ orderResponse);

            }
            else if(savedOrder.getPaymentStatus().equals(OrderConstant.PAYMENT_STATUS_CONFIRMED)){
                logger.info(" if payment-status-CONFIRMED  -- Order saved in DB With PAID :successfully: "+ savedOrder);
                data.setMessage("Payment successful for the order!! Your order is confirmed !! You will recieve the notification soon");
                data.setPaymentUrl(null);
                logger.info("Order saved in DB With PAID :successfully: "+ orderResponse);


            }
            else if(savedOrder.getPaymentStatus().equals(OrderConstant.PAYMENT_STATUS_FAILED)){
                logger.info(" if payment-status-FAILED  -- Order saved in DB With FAILED :successfully: "+ savedOrder);
                data.setMessage("Payment failed for the order!!");
                data.setPaymentUrl(null);
                logger.info("Order saved in DB With FAILED :successfully: "+ orderResponse);

            }
            else if (savedOrder.getPaymentStatus().equals(OrderConstant.PAYMENT_STATUS_TIMEOUT)){
                logger.info(" if payment-status-CANCELLED  -- Order saved in DB With CANCELLED :successfully: "+ savedOrder);
                data.setMessage("Payment timeout for the order!! Please retry the order Once again");
                data.setPaymentUrl(null);
                logger.info("Order saved in DB With CANCELLED :successfully: "+ orderResponse);

            }
            else {
                logger.info("Order saved in DB With PENDING :successfully: "+ savedOrder);
                data.setMessage("order errror!! Please retry the order Once again");
                data.setPaymentUrl(null);
                //logger.info("Order saved in DB With PENDING :successfully: "+ orderResponse);

            }
        }
        else {
            newOrder.setStatus(OrderConstant.ORDER_STATUS_CANCELLED);
            savedOrder= orderRepository.save(newOrder);
            orderResponse = processOrderwithCancelledWhenProductIsNotAvailable(userId, cartItems, trackingId);
            logger.info("Product is not available for the order: "+ cartItems.get(0).getProductId());
            data.setMessage("Product is not available for the order!! Please retry the order After sometime");
            data.setPaymentUrl(null);

        }

        orderResponse.setData(data);


        return ResponseEntity.ok(orderResponse);
    }*/

    private OrderResponse processOrderwithPendingWhenProductIsAvailable(Long userId, List<Product> cartItems,String trackingId) {
        logger.info("Order processed for user: "+ userId+ "with cartItems: "+ cartItems);
        Double totalAmount = cartItems.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
        logger.info("Total amount for the order: "+ totalAmount);

       OrderResponse orderResponse = new OrderResponse();
         orderResponse.setUserId(userId);
         orderResponse.setCardItems(cartItems);
         orderResponse.setTotalAmount(totalAmount);
         orderResponse.setTrackingId(trackingId);
         orderResponse.setStatus(OrderConstant.ORDER_STATUS_PENDING);
         orderResponse.setCreatedAt(LocalDateTime.now());
         orderResponse.setUpdatedAt(LocalDateTime.now());
         orderResponse.setEmailNotificationSent(false);
         orderResponse.setPhoneNotificationSent(false);
         logger.info("Order processed successfully: "+ orderResponse);
         return orderResponse;

    }
    private OrderResponse processOrderwithCancelledWhenProductIsNotAvailable(Long userId, List<Product> cartItems,String trackingId) {
        logger.info("Order processed for user: "+ userId+ "with cartItems: "+ cartItems);
        Double totalAmount = cartItems.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
        logger.info("Total amount for the order: "+ totalAmount);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setUserId(userId);
        orderResponse.setCardItems(cartItems);
        orderResponse.setTotalAmount(0.0);
        orderResponse.setTrackingId(trackingId);
        orderResponse.setStatus(OrderConstant.ORDER_STATUS_CANCELLED);
        orderResponse.setCreatedAt(LocalDateTime.now());
        orderResponse.setUpdatedAt(LocalDateTime.now());
        orderResponse.setEmailNotificationSent(false);
        orderResponse.setPhoneNotificationSent(false);
        logger.info("processOrderwithCancelledWhenProductIsNotAvailable "+ orderResponse);
        return orderResponse;

    }
    private  boolean checkProductAvailability(Long productId, Long quantity,String bearerToken) {
        ProductAvailabilityResponse productAvailabilityResponse = productServiceClient.checkProductAvailability(productId, quantity,bearerToken);
        logger.info("ProductAvailabilityResponse : "+productAvailabilityResponse);
        return productAvailabilityResponse.isAvailable();
    }
    private  boolean checkProductAvailabilityList(List<Product> cartItems,String bearerToken) {
        logger.info("checkProductAvailabilityList : "+cartItems);
        try{
            ProductAvailabilityListResponse productAvailabilityResponse = productServiceClient.checkProductAvailabilityList(cartItems,bearerToken);
           if(productAvailabilityResponse.isAvailable() == false)  {
                logger.info("ProductAvailabilityResponse is "+ productAvailabilityResponse.isAvailable());
                throw new ProductNotFoundException("Product is not available for the order or required quantity os not available !! Try with less quantity");
           }
             //  throw new RuntimeException("ProductAvailabilityResponse is null");
            //logger.info("ProductAvailabilityResponse : "+productAvailabilityResponse);
            return productAvailabilityResponse.isAvailable();
        }
        catch (Exception e){
            logger.error("Error in checkProductAvailabilityList : "+e.getMessage());
        }
        return false;

    }

    private boolean requiredQuantityAvailable(List<Product> cartItems) {
        return true;
    }
    public Order updateOrder(Long orderId, OrderRequestDto orderRequestDto) {
        return null;
    }

    public List<OrderResponseById> getAllOrdersByUserId(Long userId) {
        if(userId!=null){
            logger.info("userId  {} : ",userId);
            List<Order> orders = orderRepository.findByUserId(userId);
            if(orders == null) {
                throw new NoOrderFoundException("No order found for the user: "+ userId);
            }
           // convert List<Order> intto List <OrderResponse>
           List<OrderResponseById> orderResponseList = orders.stream().map(order -> modelMapper.map(order, OrderResponseById.class)).toList();
            return orderResponseList;
        }
            throw new UserNoFoundException("User not found with the userid : "+userId);
    }

    public OrderResponseById getOrderByOrderId(Long orderId) {
            logger.info("Get order by orderId Request recieved : "+ orderId);
            if (orderId!=null){
                Order order = orderRepository.findByOrderId(orderId).orElse(null);
               List<OrderProduct>  orderProduct = orderProductRepository.findByOrderId(orderId);
               logger.info("orderproduct List : "+orderProduct);
                if(orderProduct!=null && orderProduct.size()>0){
                    order.setOrderProductList(orderProduct);
                }
                logger.info("Order found : "+ order);
                if(order == null) {
                    throw new NoOrderFoundException("No order found with the orderId: "+ orderId);
                }
              OrderResponseById rsp =  modelMapper.map(order, OrderResponseById.class);
                logger.info("Order found : "+ rsp);
                return rsp;
            }
            throw new NoOrderFoundException("No order found with the orderId: "+ orderId);
    }

    public OrderResponse deleteOrderByOrderId(Long orderId) {
        logger.info("Delete order by orderId Request recieved : "+ orderId);
        if(orderId!=null){
            Order order = orderRepository.findByOrderId(orderId).orElse(null);
            if(order == null) {
                throw new NoOrderFoundException("No order found with the orderId: "+ orderId);
            }
            orderRepository.delete(order);
            return modelMapper.map(order, OrderResponse.class);
        }
        else{
            throw  new NoOrderFoundException("No order found with the orderId: "+ orderId);
        }

    }
    public void updateOrderStatus(Long orderId, Long userId, String paymentStatus, String status,String bearerToken) {
        logger.info("Update order status by orderId Request recieved : "+ orderId);
        if(orderId!=null && userId!=null && paymentStatus!=null && status!=null){
                if(paymentStatus.equals("SUCCESS")){
                    orderRepository.updateOrderStatus(orderId, userId, paymentStatus, status);
                    logger.info("Order status updated successfully for order id: "+ orderId);
                    try{
                        productServiceClient.updateOrderStatus(orderProductMap.get(orderId),bearerToken);
                        logger.info("Product status updated successfully for order id: "+ orderId);
                    }
                    catch (Exception e){
                        logger.error("Error in updating product status for order id: "+ orderId);
                    }
                }
                else if (paymentStatus.equals("FAILURE")){
                    orderRepository.updateOrderStatus(orderId, userId, paymentStatus, status);

                }
                else if (paymentStatus.equals("TIMEOUT")){
                    orderRepository.updateOrderStatus(orderId, userId, paymentStatus, status);
                }
                else {
                    throw new RuntimeException("Invalid payment status for order id: "+ orderId);
                }

        }
        else{
            throw new NoOrderFoundException("No order found with the orderId: "+ orderId);
        }
    }

   /* public void senUpdateToProductServiceForProductsAndQuantityReverals(List<Product> cartItems) {
        logger.info("senUpdateToProductServiceForProductsAndQuantityReverals : "+cartItems);
        productServiceClient.updateProductQuantity(cartItems);
    }*/
}
