package com.quickbite.auth_service.repository;

import com.quickbite.auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository gives you save(), findAll(), findById(), delete() for free
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring generates the SQL for this automatically from the method name
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
