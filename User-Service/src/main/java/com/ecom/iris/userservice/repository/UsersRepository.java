package com.ecom.iris.userservice.repository;

import com.ecom.iris.userservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {


    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailAndUsername(String email, String username);

    Users findByUsername(String username);

    Optional<Users> findByUserId(Long userId);

    Users deleteByUserId(Long userId);
}

