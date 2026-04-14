package com.nibin.fintech.modules.auth.service;

import com.nibin.fintech.core.exception.ApiException;
import com.nibin.fintech.core.util.JwtUtil;
import com.nibin.fintech.modules.user.entity.User;
import com.nibin.fintech.modules.user.entity.enums.Status;
import com.nibin.fintech.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new ApiException("User not verified");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApiException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}