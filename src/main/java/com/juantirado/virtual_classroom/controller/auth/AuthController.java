package com.juantirado.virtual_classroom.controller.auth;

import com.juantirado.virtual_classroom.dto.auth.AuthRequestDto;
import com.juantirado.virtual_classroom.dto.auth.RegisterRequestDto;
import com.juantirado.virtual_classroom.dto.auth.TokenResponseDto;
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
    public ResponseEntity<TokenResponseDto> register(@RequestBody RegisterRequestDto request) {
        final TokenResponseDto response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authenticate(@RequestBody AuthRequestDto request) {
        final TokenResponseDto response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public TokenResponseDto refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication
    )
    {
        return service.refreshToken(authentication);
    }


}