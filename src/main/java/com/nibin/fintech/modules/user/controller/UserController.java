package com.nibin.fintech.modules.user.controller;

import com.nibin.fintech.modules.user.dto.RegisterRequest;
import com.nibin.fintech.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return "User registered successfully. OTP sent.";
    }
}