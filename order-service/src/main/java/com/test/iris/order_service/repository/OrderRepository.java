package com.test.iris.order_service.repository;


import com.test.iris.order_service.model.Order;
import com.test.iris.order_service.model.OrderProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {


    List<Order> findByUserId(Long userId);

    Optional<Order> findByOrderId(Long orderId);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.paymentStatus = :paymentStatus, o.status =:status WHERE o.orderId = :orderId AND o.userId = :userId")
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("userId") Long userId, @Param("paymentStatus") String paymentStatus,@Param("status") String status);
}
