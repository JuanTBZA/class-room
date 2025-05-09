package com.juantirado.virtual_classroom.repository.auth;

import com.juantirado.virtual_classroom.model.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
