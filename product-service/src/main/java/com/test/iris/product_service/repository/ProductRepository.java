package com.test.iris.product_service.repository;

import com.test.iris.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Optional<Product> findByProductId(Long productId);
    void deleteByProductId(Long productId);
    List<Product> findByCategory(String category);
}
