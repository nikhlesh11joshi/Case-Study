package com.test.iris.payment_service.service;

import com.test.iris.payment_service.client.CardvalidationServiceClient;
import com.test.iris.payment_service.client.OrderServiceClient;
import com.test.iris.payment_service.exception.InvalidCardException;
import com.test.iris.payment_service.exception.PaymentNotFoundException;
import com.test.iris.payment_service.exception.PaymentUpdateException;
import com.test.iris.payment_service.model.Payment;
import com.test.iris.payment_service.repository.PaymentRepository;
import com.test.iris.payment_service.request.CardDetailsRequest;
import com.test.iris.payment_service.request.CardValidationResponse;
import com.test.iris.payment_service.request.PaymentConfirmationStatusRequestDto;
import com.test.iris.payment_service.request.PaymentRequestDto;
import com.test.iris.payment_service.response.PaymentConfirmationStatusResponseDto;
import com.test.iris.payment_service.response.PaymentResponseDto;
import com.test.iris.payment_service.util.PaymentUtil;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.random.RandomGenerator;

@Service
public class PaymentService {

    private Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CardvalidationServiceClient cardValidationService;

    @Autowired
    private OrderServiceClient orderServiceClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${payment.gateway.url}")
    private String paymentGatewayUrl;

    private static final String SUCCESS ="SUCCESS";
    private static final String FAILED ="FAILED";
    private static final String INVALID ="INVALID";
    private static final String TIMEOUT= "TIMEOUT";
    private static final String PENDING= "PENDING";
    private static final String CANCELLED= "CANCELLED";
    private static final String CHARGEBACK= "CHARGEBACK";

    public ResponseEntity<PaymentResponseDto> createPayment(PaymentRequestDto paymentRequestDto,String bearerToken) {
       // boolean isCardValidate =false;
        logger.info("Creating payment for order id: {}", paymentRequestDto.getOrderId());
        Payment payment = new Payment();
        payment.setOrderId(paymentRequestDto.getOrderId());
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setPaymentStatus(PENDING);
        payment.setPaymentGateway(paymentRequestDto.getPaymentGateway());
        payment.setPaymentMethod(paymentRequestDto.getPaymentMethod());
        payment.setUserId(paymentRequestDto.getUserId());
        payment.setTransactionId("INP-"+System.currentTimeMillis());
        payment.setPaymentId(RandomGenerator.getDefault().nextLong());
        CardDetailsRequest cardDetailsRequest = paymentRequestDto.getCardDetailsRequest();
        logger.info("Validating card details for order id: {}", paymentRequestDto.getOrderId());
        logger.info("payment details: {}", payment);
        if(isValidateCard(cardDetailsRequest,bearerToken)){
            logger.info("Card validation is success .. Now sending this request to payment gateway for payment completion");
            paymentRepository.save(payment);
            ResponseEntity<PaymentResponseDto> paymentResponseDto = processPayment(payment);
            if(paymentResponseDto.getStatusCode().equals(HttpStatusCode.valueOf(200)) && paymentResponseDto.getBody().getPaymentStatus().equals(SUCCESS)){
                payment.setPaymentStatus(SUCCESS);
                payment.setTransactionId(paymentResponseDto.getBody().getTransactionId());
                payment.setUpdatedAt(LocalDateTime.now());
                logger.info("payment success status updating in Database:"+ payment);
              Payment savendPayment=  paymentRepository.save(payment);
                logger.info("payment success status updated in Database: savendPayment : "+ savendPayment);
                //send email and mobile notification to user with Payment success and order details and tracking ID of the order::
                // sending a notification to Order service to update the order status to confirmed
                if(payment.getPaymentStatus().equals("SUCCESS")){
                    PaymentConfirmationStatusRequestDto paymentConfirmationStatusRequestDto = new PaymentConfirmationStatusRequestDto();
                    paymentConfirmationStatusRequestDto.setPaymentStatus("SUCCESS");
                    paymentConfirmationStatusRequestDto.setOrderId(paymentRequestDto.getOrderId());
                    paymentConfirmationStatusRequestDto.setUserId(paymentRequestDto.getUserId());
                    sendOrderConfirmationNotification(paymentConfirmationStatusRequestDto,bearerToken);

                }

                    return ResponseEntity.ok(paymentResponseDto.getBody());
            }
            else if(paymentResponseDto.getStatusCode().equals(HttpStatusCode.valueOf(404)) && paymentResponseDto.getBody().getPaymentStatus().equals(FAILED)){
                payment.setPaymentStatus(FAILED);
                payment.setTransactionId(paymentResponseDto.getBody().getTransactionId());
                paymentRepository.save(payment);
                logger.info("payment failed status updated in Database: due to not found");
                //send email and mobile notification to user with Payment success and order details and tracking ID of the order::
                // sending a notification to Order service to update the order status to confirmed
                if(payment.getPaymentStatus().equals("FAILED")){
                    PaymentConfirmationStatusRequestDto paymentConfirmationStatusRequestDto = new PaymentConfirmationStatusRequestDto();
                    paymentConfirmationStatusRequestDto.setPaymentStatus("FAILED");
                    paymentConfirmationStatusRequestDto.setOrderId(paymentRequestDto.getOrderId());
                    paymentConfirmationStatusRequestDto.setUserId(paymentRequestDto.getUserId());
                    sendOrderConfirmationNotification(paymentConfirmationStatusRequestDto,bearerToken);

                }
                return ResponseEntity.ok(paymentResponseDto.getBody());
            }
            else if(paymentResponseDto.getStatusCode().equals(HttpStatusCode.valueOf(406)) && paymentResponseDto.getBody().getPaymentStatus().equals(CANCELLED)){
                payment.setPaymentStatus(CANCELLED);
                payment.setTransactionId(paymentResponseDto.getBody().getTransactionId());
                paymentRepository.save(payment);
                logger.info("payment failed status updated in Database: due to not found");
                //send email and mobile notification to user with Payment success and order details and tracking ID of the order::
                // sending a notification to Order service to update the order status to confirmed
                // order service will send a notification to Product service to reverse the product details
                // send a notification to user for the refund of the order details amount.
                if(payment.getPaymentStatus().equals("CANCELLED")){
                    PaymentConfirmationStatusRequestDto paymentConfirmationStatusRequestDto = new PaymentConfirmationStatusRequestDto();
                    paymentConfirmationStatusRequestDto.setPaymentStatus("CANCELLED");
                    paymentConfirmationStatusRequestDto.setOrderId(paymentRequestDto.getOrderId());
                    paymentConfirmationStatusRequestDto.setUserId(paymentRequestDto.getUserId());
                    sendOrderConfirmationNotification(paymentConfirmationStatusRequestDto,bearerToken);

                }
                return ResponseEntity.ok(paymentResponseDto.getBody());
            }
            else if(paymentResponseDto.getStatusCode().equals(HttpStatusCode.valueOf(408)) && paymentResponseDto.getBody().getPaymentStatus().equals(TIMEOUT)){
                payment.setPaymentStatus(TIMEOUT);
                payment.setTransactionId(paymentResponseDto.getBody().getTransactionId());
                paymentRepository.save(payment);
                logger.info("payment failed status updated in Database: due to not found");
                //send email and mobile notification to user with Payment success and order details and tracking ID of the order::
                // sending a notification to Order service to update the order status to confirmed
                // order service will send a notification to Product service to reverse the product details
                // send a notification to user for the refund of the order details amount.
                if(payment.getPaymentStatus().equals(TIMEOUT)){
                    PaymentConfirmationStatusRequestDto paymentConfirmationStatusRequestDto = new PaymentConfirmationStatusRequestDto();
                    paymentConfirmationStatusRequestDto.setPaymentStatus(TIMEOUT);
                    paymentConfirmationStatusRequestDto.setOrderId(paymentRequestDto.getOrderId());
                    paymentConfirmationStatusRequestDto.setUserId(paymentRequestDto.getUserId());
                    sendOrderConfirmationNotification(paymentConfirmationStatusRequestDto,bearerToken);

                }
                return ResponseEntity.ok(paymentResponseDto.getBody());
            }
            else {
                payment.setPaymentStatus(FAILED);
                payment.setTransactionId(paymentResponseDto.getBody().getTransactionId());
                paymentRepository.save(payment);
                //send email and mobile notification to user with Payment success and order details and tracking ID of the order::
                // sending a notification to Order service to update the order status to confirmed
                // order service will send a notification to Product service to reverse the product details
                // send a notification to user for the refund of the order details amount.
                if(payment.getPaymentStatus().equals(FAILED)){
                    PaymentConfirmationStatusRequestDto paymentConfirmationStatusRequestDto = new PaymentConfirmationStatusRequestDto();
                    paymentConfirmationStatusRequestDto.setPaymentStatus(FAILED);
                    paymentConfirmationStatusRequestDto.setOrderId(paymentRequestDto.getOrderId());
                    paymentConfirmationStatusRequestDto.setUserId(paymentRequestDto.getUserId());
                    sendOrderConfirmationNotification(paymentConfirmationStatusRequestDto,bearerToken);

                }
                return ResponseEntity.ok(paymentResponseDto.getBody());
            }
        }
        else {
            throw new InvalidCardException("Card details are invalid!! Please check the card details and re-try again!! Thanks!!");
        }


      /*  PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setOrderId(payment.getOrderId());
        paymentResponseDto.setPaymentStatus(payment.getPaymentStatus());
        paymentResponseDto.setAmount(payment.getAmount());*/
       // return ResponseEntity.ok(paymentResponseDto);
    }

private ResponseEntity<PaymentConfirmationStatusResponseDto> sendOrderConfirmationNotification(PaymentConfirmationStatusRequestDto paymentConfirmationStatusRequestDto,String bearerToken){
    ResponseEntity<PaymentConfirmationStatusResponseDto> paymentConfirmationStatusResponseDto =null;
    if(paymentConfirmationStatusRequestDto!=null){
        logger.info("Sending order confirmation notification to Order service for order id: {}", paymentConfirmationStatusRequestDto.getOrderId());
        try {
            paymentConfirmationStatusResponseDto = orderServiceClient.sendPaymentConfirmationNotificationToOrderService(paymentConfirmationStatusRequestDto,bearerToken);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        if (paymentConfirmationStatusResponseDto != null && paymentConfirmationStatusResponseDto.getBody().getStatus().equals("SUCCESS")) {
            return paymentConfirmationStatusResponseDto;
        }
       /* else if(paymentConfirmationStatusResponseDto != null && paymentConfirmationStatusResponseDto.getBody().getStatus().equals("FAILURE")){

            throw new PaymentUpdateException("Payment is failed for order id: "+ paymentConfirmationStatusRequestDto.getOrderId() +" and user id: "+ paymentConfirmationStatusRequestDto.getUserId());
        }
        else if(paymentConfirmationStatusResponseDto != null && paymentConfirmationStatusResponseDto.getBody().getStatus().equals("TIMEOUT")){
            throw new PaymentUpdateException("Payment is failed for order id: "+ paymentConfirmationStatusRequestDto.getOrderId() +" and user id: "+ paymentConfirmationStatusRequestDto.getUserId());
        }
        else if (paymentConfirmationStatusResponseDto != null && paymentConfirmationStatusResponseDto.getBody().getStatus().equals("CANCELLED")) {
            throw new PaymentUpdateException("Payment is failed for order id: "+ paymentConfirmationStatusRequestDto.getOrderId() +" and user id: "+ paymentConfirmationStatusRequestDto.getUserId());
        }
        else if (paymentConfirmationStatusResponseDto != null && paymentConfirmationStatusResponseDto.getBody().getStatus().equals("CHARGEBACK")) {
            throw new PaymentUpdateException("Payment is failed for order id: "+ paymentConfirmationStatusRequestDto.getOrderId() +" and user id: "+ paymentConfirmationStatusRequestDto.getUserId());
        }
        else {
            throw new PaymentUpdateException("Payment is failed for order id: "+ paymentConfirmationStatusRequestDto.getOrderId() +" and user id: "+ paymentConfirmationStatusRequestDto.getUserId());
        }*/
    }
    else {
        throw new PaymentNotFoundException("Payment details are invalid!! Please check the payment details and re-try again!! Thanks!!");
    }

    return null;

}
    private boolean isValidateCard(CardDetailsRequest cardDetailsRequest, String bearerToken) {
        ResponseEntity<CardValidationResponse> cardValidationResponse =null;
        if(cardDetailsRequest!=null) {
            logger.info("Validating card for card number: {}", cardDetailsRequest.getCardNumber());
            try {
                logger.info("Sending card validation request to Validation service : {}", cardDetailsRequest.getCardNumber());
                cardValidationResponse = cardValidationService.validateCard(cardDetailsRequest,bearerToken);
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
            if (cardValidationResponse != null && cardValidationResponse.getBody().isValid()) {
                return true;
            }
        }
        else {
            throw new InvalidCardException("Card details are invalid!! Please check the card details and re-try again!! Thanks!!");
        }

        return false;
    }

    private ResponseEntity<PaymentResponseDto> processPayment(Payment payment) {
        logger.info("processpayment : {} : "+payment);
        if(payment!=null){
            logger.info("Processing payment for order id: {}", payment.getOrderId() +" and user id: "+ payment.getUserId());
            if(paymentGatewayUrl!=null){
                logger.info("Payment gateway url is: {}", paymentGatewayUrl);
                ResponseEntity<PaymentResponseDto> paymentResponseEntity = restTemplate.postForEntity(paymentGatewayUrl, payment, PaymentResponseDto.class);
                logger.info("Payment response: {}", paymentResponseEntity.getBody());
                return paymentResponseEntity;
            }
            else {
                throw new RuntimeException("Payment gateway url is not configured properly!! Please check the configuration and re-try again!! Thanks!!");
            }
        }
        else {
            throw new RuntimeException("Payment details are invalid!! Please check the payment details and re-try again!! Thanks!!");

        }
    }

    public ResponseEntity<PaymentResponseDto> getPaymentByPaymentId(Long paymentId,Long userId) {
        logger.info("Getting order details for payment id: {}", paymentId);
        if(paymentId!=null){
            Optional<Payment> paymentOptional = paymentRepository.findByUserIdAndPaymentId(userId,paymentId);
            if (paymentOptional.isPresent()) {
                Payment payment = paymentOptional.get();
                PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
                paymentResponseDto.setOrderId(payment.getOrderId());
                paymentResponseDto.setUserId(payment.getUserId());
                paymentResponseDto.setAmount(payment.getAmount());
                paymentResponseDto.setTransactionId(payment.getTransactionId());
                paymentResponseDto.setPaymentStatus(payment.getPaymentStatus());
                paymentResponseDto.setStatusCode(200);
                return ResponseEntity.ok(paymentResponseDto);
            }
            else {
                throw new PaymentNotFoundException("Payment details are invalid!! Please check the payment details and re-try again!! Thanks!!");
            }

        }
        else {
            throw new PaymentNotFoundException("Payment id is invalid!! Please check the payment id and re-try again!! Thanks!!");
        }
    }


    public ResponseEntity<PaymentResponseDto> updatePaymentByPaymentId(Long paymentId, PaymentRequestDto paymentRequestDto,String bearerToken) {
        ResponseEntity<PaymentResponseDto> paymentResponseEntity =null;
        logger.info("Updating order details for payment id: {}", paymentId);
        if (paymentId != null && paymentRequestDto.getOrderId() != null) {
            Payment payment = paymentRepository.findByUserIdAndPaymentIdAndOrderId(paymentRequestDto.getUserId(), paymentId, paymentRequestDto.getOrderId()).orElse(null);
            if (payment != null) {
                if (!payment.getPaymentStatus().equals(SUCCESS)) {
                    if (payment.getUpdatedAt().isBefore(LocalDateTime.now().minusMinutes(30))) {
                        CardDetailsRequest cardDetailsRequest = paymentRequestDto.getCardDetailsRequest();
                        logger.info("Validating card details for order id: {}", paymentRequestDto.getOrderId());
                        if (isValidateCard(cardDetailsRequest,bearerToken)) {
                            logger.info("Card validation is success .. Now sending this request to payment gateway for payment completion");
                                try{
                                     paymentResponseEntity = processPayment(payment);
                                     if(paymentResponseEntity.getStatusCode().equals(200) && paymentResponseEntity.getBody().getPaymentStatus().equals(SUCCESS)){
                                        logger.info("Payment is success for order id: {}", paymentRequestDto.getOrderId() +" transaction Id: "+ payment.getTransactionId());
                                        payment.setPaymentStatus(paymentResponseEntity.getBody().getPaymentStatus());
                                        payment.setUpdatedAt(LocalDateTime.now());
                                        paymentRepository.save(payment);
                                    }
                                     else {
                                         logger.info("Payment is failed for order id: {}", paymentRequestDto.getOrderId() +" transaction Id: "+ payment.getTransactionId());
                                         payment.setPaymentStatus(paymentResponseEntity.getBody().getPaymentStatus());
                                         payment.setUpdatedAt(LocalDateTime.now());
                                         paymentRepository.save(payment);
                                     }
                                }
                                catch (Exception ex){
                                    logger.error("Error while processing payment: {}", ex.getMessage());
                                    throw new RuntimeException("Error while processing payment: "+ ex.getMessage());
                                }
                            return ResponseEntity.ok(paymentResponseEntity.getBody());

                        } else {
                            throw new InvalidCardException("Card details are invalid!! Please check the card details and re-try again!! Thanks!!");
                        }
                    } else {
                        throw new PaymentUpdateException("Payment is in process !! Please try again after 30 mins of previous payment created: " + payment.getUpdatedAt());
                    }

                } else {
                    throw new PaymentUpdateException("Payment is already SUCCESS for the order id: " + paymentRequestDto.getOrderId() + " and payment id: " + paymentId + "payment status is :" + payment.getPaymentStatus());
                }

            } else {
                throw new PaymentNotFoundException("Payment details are invalid!! Please check the payment details and re-try again!! Thanks!!");
            }
        } else {
            throw new PaymentUpdateException("Payment id is invalid!! Please check the payment id and re-try again!! Thanks!!");
        }
    }

}
