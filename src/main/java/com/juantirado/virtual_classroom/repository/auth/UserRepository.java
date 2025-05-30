package com.juantirado.virtual_classroom.repository.auth;

import com.juantirado.virtual_classroom.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
