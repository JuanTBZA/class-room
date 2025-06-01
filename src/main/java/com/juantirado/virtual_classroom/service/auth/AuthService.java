package com.juantirado.virtual_classroom.service.auth;


import com.juantirado.virtual_classroom.dto.auth.AuthRequestDto;
import com.juantirado.virtual_classroom.dto.auth.RegisterRequestDto;
import com.juantirado.virtual_classroom.dto.auth.TokenResponseDto;
import com.juantirado.virtual_classroom.entity.auth.Role;
import com.juantirado.virtual_classroom.entity.auth.Token;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.repository.auth.RoleRepository;
import com.juantirado.virtual_classroom.repository.auth.TokenRepository;
import com.juantirado.virtual_classroom.repository.auth.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponseDto register(final RegisterRequestDto request) {

        Role userRole = roleRepository.findByName("ROLE_" + request.getRole().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Rol no configurado: " + request.getRole()));

        final User user = User.builder()
                .role(userRole)
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dni(request.getDni())
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .enabled(true)
                .build();

        final User savedUser = repository.save(user);
        final String jwtToken = jwtService.generateToken(savedUser);
        final String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, jwtToken);
        return new TokenResponseDto(jwtToken, refreshToken);
    }


    public User findOrCreateByEmail(String email, String name) {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("default role not configured"));

        return repository.findByEmail(email)
                .orElseGet(() -> repository.save(User
                        .builder()
                        .role(userRole)
                        .name(name)
                        .email(email)
                        .password(passwordEncoder.encode("redildigital"))
                        .build()));
    }


    public TokenResponseDto authenticate(final AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        final User user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        final String accessToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponseDto(accessToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        final Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final User user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setIsExpired(true);
                token.setIsRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponseDto refreshToken(@NotNull final String authentication) {

        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }
        final String refreshToken = authentication.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            return null;
        }

        final User user = this.repository.findByEmail(userEmail).orElseThrow();
        final boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);
        if (!isTokenValid) {
            return null;
        }

        final String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new TokenResponseDto(accessToken, refreshToken);
    }
}