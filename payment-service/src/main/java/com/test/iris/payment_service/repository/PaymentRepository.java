package com.test.iris.payment_service.repository;

import com.test.iris.payment_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    //Payment findByOrderId(Long orderId);
    Payment findByUserId(Long userId);
    Optional<Payment> findById(Long id);
    Optional<Payment> findByPaymentId(Long paymentId);
    Optional<Payment> findByTransactionId(String transactionId);
    Optional<Payment> findByOrderId(Long orderId);
    Optional<Payment> findByUserIdAndOrderId(Long userId, Long orderId);
    Optional<Payment> findByUserIdAndPaymentId(Long userId, Long paymentId);
    Optional<Payment> findByUserIdAndPaymentIdAndOrderId(Long userId, Long paymentId, Long orderId);

}
