package com.nibin.fintech.modules.auth.controller;

import com.nibin.fintech.modules.auth.service.AuthService;
import com.nibin.fintech.modules.auth.dto.request.LoginRequest;
import com.nibin.fintech.modules.auth.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}