package com.juantirado.virtual_classroom.repository.auth;

import com.juantirado.virtual_classroom.model.entity.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
