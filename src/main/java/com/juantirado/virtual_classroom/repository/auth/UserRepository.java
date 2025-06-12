package com.juantirado.virtual_classroom.repository.auth;

import com.juantirado.virtual_classroom.entity.auth.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("""
        SELECT u FROM User u
        WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :filtro, '%'))
           OR LOWER(u.dni) LIKE LOWER(CONCAT('%', :filtro, '%'))
    """)
    Page<User> findUsersByFiltro(@Param("filtro") String filtro, Pageable pageable);
}
