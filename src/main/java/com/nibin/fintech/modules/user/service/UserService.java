package com.nibin.fintech.modules.user.service;

import com.nibin.fintech.modules.otp.entity.Otp;
import com.nibin.fintech.modules.otp.repository.OtpRepository;
import com.nibin.fintech.modules.user.dto.request.RegisterRequest;
import com.nibin.fintech.modules.user.entity.User;
import com.nibin.fintech.modules.user.entity.enums.Status;
import com.nibin.fintech.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final OtpRepository otpRepository;

    public void registerUser(RegisterRequest request) {

        // 1. Check duplicates
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new RuntimeException("Mobile already exists");
        }

        // 2. Create user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        // 3. Generate OTP
        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        System.out.println("Generated OTP: " + code);

        // 4. Delete old OTP if exists
        otpRepository.findByUser(user)
                .ifPresent(otpRepository::delete);

        // 5. Save new OTP
        Otp otp = Otp.builder()
                .otp(code)
                .user(user)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        otpRepository.save(otp);
    }

    public void verifyOtp(String email, String otpCode) {

        // 1. Find user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get OTP
        Otp otp = otpRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        // 3. Check already verified
        if (otp.isVerified()) {
            throw new RuntimeException("OTP already used");
        }

        // 4. Check expiry
        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        // 5. Validate OTP
        if (!otp.getOtp().equals(otpCode)) {
            throw new RuntimeException("Invalid OTP");
        }

        // 6. Mark OTP as verified
        otp.setVerified(true);
        otpRepository.save(otp);

        // 7. Activate user
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }
}