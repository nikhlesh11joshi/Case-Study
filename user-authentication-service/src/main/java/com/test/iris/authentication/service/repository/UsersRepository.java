package com.test.iris.authentication.service.repository;


import com.test.iris.authentication.service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {


    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailAndUsername(String email, String username);

    Optional<Users> findByUsername(String username);


}

