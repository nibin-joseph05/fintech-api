package com.nibin.fintech.modules.user.service;

import com.nibin.fintech.modules.user.dto.RegisterRequest;
import com.nibin.fintech.modules.user.entity.User;
import com.nibin.fintech.modules.user.entity.enums.Status;
import com.nibin.fintech.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(RegisterRequest request) {

        // Check duplicates
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new RuntimeException("Mobile already exists");
        }

        // Generate OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        System.out.println("Generated OTP: " + otp);

        // Create user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .password(request.getPassword())
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}