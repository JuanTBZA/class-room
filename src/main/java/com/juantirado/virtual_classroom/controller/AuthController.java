package com.juantirado.virtual_classroom.controller;

import com.juantirado.virtual_classroom.dto.auth.AuthRequestDTO;
import com.juantirado.virtual_classroom.dto.auth.RegisterRequestDTO;
import com.juantirado.virtual_classroom.dto.auth.TokenResponseDTO;
import com.juantirado.virtual_classroom.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        final TokenResponseDTO response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> authenticate(@RequestBody AuthRequestDTO request) {
        final TokenResponseDTO response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public TokenResponseDTO refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication
    )
    {
        return service.refreshToken(authentication);
    }


}