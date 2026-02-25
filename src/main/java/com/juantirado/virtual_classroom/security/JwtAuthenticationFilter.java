package com.juantirado.virtual_classroom.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.repository.auth.TokenRepository;
import com.juantirado.virtual_classroom.repository.auth.UserRepository;
import com.juantirado.virtual_classroom.service.auth.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {


        // Saltar filtro para endpoints de autenticación
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // Validar header Authorization
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        final String userEmail;

        try {
            userEmail = jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException e) {
            // Responder con JSON estructurado cuando el token ha expirado
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", java.time.Instant.now().toString());
            body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            body.put("error", "Unauthorized");
            body.put("detail", "El token ha expirado");
            body.put("path", request.getServletPath());

            String json = new ObjectMapper().writeValueAsString(body);

            response.getWriter().write(json);
            return;
        } catch (Exception e) {
            // Responder con JSON estructurado cuando el token es inválido
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", java.time.Instant.now().toString());
            body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            body.put("error", "Unauthorized");
            body.put("detail", "Token inválido");
            body.put("path", request.getServletPath());

            String json = new ObjectMapper().writeValueAsString(body);

            response.getWriter().write(json);
            return;
        }

        if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Verificar token en base de datos
        boolean isTokenValid = tokenRepository.findByValue(jwt)
                .map(token -> !token.getIsExpired() && !token.getIsRevoked())
                .orElse(false);

        if (isTokenValid) {
            Optional<User> userOptional = userRepository.findByEmail(userEmail);

            if (userOptional.isPresent() && jwtService.isTokenValid(jwt, userOptional.get())) {
                // Extraer authorities del token de forma segura
                Claims claims = jwtService.extractAllClaims(jwt);
                Object authObj = claims.get("authorities");
                List<String> authorities;
                if (authObj instanceof List<?> list) {
                    authorities = list.stream().map(Object::toString).toList();
                } else {
                    authorities = List.of();
                }

                // Construir UserDetails con los authorities del token
                UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                        .username(userEmail)
                        .password("") // No necesario para autenticación JWT
                        .authorities(authorities.stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList())
                        .build();

                // Crear autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}