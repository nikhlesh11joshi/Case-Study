package com.test.iris.card_validation_service.repository;

import com.test.iris.card_validation_service.model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Cards, Long> {
    Cards findByCardNumber(String cardNumber);
    Cards findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}