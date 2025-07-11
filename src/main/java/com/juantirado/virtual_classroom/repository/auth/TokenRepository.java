package com.juantirado.virtual_classroom.repository.auth;

import com.juantirado.virtual_classroom.entity.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.isExpired = false or t.isRevoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByValue(String token);
}