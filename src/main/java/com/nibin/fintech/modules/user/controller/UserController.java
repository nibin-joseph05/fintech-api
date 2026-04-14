package com.nibin.fintech.modules.user.controller;

import com.nibin.fintech.modules.user.dto.request.VerifyOtpRequest;
import com.nibin.fintech.modules.user.dto.request.RegisterRequest;
import com.nibin.fintech.modules.user.dto.response.VerifyOtpResponse;
import com.nibin.fintech.modules.user.dto.response.RegisterResponse;
import com.nibin.fintech.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok(new RegisterResponse("User registered successfully. OTP sent."));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<VerifyOtpResponse> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request
    ) {
        userService.verifyOtp(request.getEmail(), request.getOtp());
        return ResponseEntity.ok(new VerifyOtpResponse("User verified successfully"));
    }
}