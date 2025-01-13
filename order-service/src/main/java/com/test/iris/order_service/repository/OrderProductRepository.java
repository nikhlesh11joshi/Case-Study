package com.test.iris.order_service.repository;

import com.test.iris.order_service.model.Order;
import com.test.iris.order_service.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
   // Optional<OrderProduct> findByOrderId(Long orderId);
   // List<OrderProduct> findByOrderId(Long orderId);
   @Query("SELECT op FROM OrderProduct op WHERE op.order.id = :orderId")
   List<OrderProduct> findByOrderId(@Param("orderId") Long orderId);
   // List<OrderProduct> findByOrderId(Long orderId);
}
