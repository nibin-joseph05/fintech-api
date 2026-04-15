package com.nibin.fintech.modules.user.service;

import com.nibin.fintech.core.exception.ApiException;
import com.nibin.fintech.modules.account.entity.Account;
import com.nibin.fintech.modules.account.repository.AccountRepository;
import com.nibin.fintech.modules.otp.entity.Otp;
import com.nibin.fintech.modules.otp.repository.OtpRepository;
import com.nibin.fintech.modules.user.dto.request.RegisterRequest;
import com.nibin.fintech.modules.user.entity.User;
import com.nibin.fintech.modules.user.entity.enums.Status;
import com.nibin.fintech.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final OtpRepository otpRepository;

    private final AccountRepository accountRepository;

    public void registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already exists");
        }

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new ApiException("Mobile already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        System.out.println("Generated OTP: " + code);

        otpRepository.findByUser(user)
                .ifPresent(otpRepository::delete);

        Otp otp = Otp.builder()
                .otp(code)
                .user(user)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        otpRepository.save(otp);
    }

    public void verifyOtp(String email, String otpCode) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found"));

        Otp otp = otpRepository.findByUser(user)
                .orElseThrow(() -> new ApiException("OTP not found"));

        if (otp.isVerified()) {
            throw new ApiException("OTP already used");
        }

        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new ApiException("OTP expired");
        }

        if (!otp.getOtp().equals(otpCode)) {
            throw new ApiException("Invalid OTP");
        }

        otp.setVerified(true);
        otpRepository.save(otp);

        user.setStatus(Status.ACTIVE);
        userRepository.save(user);

        Account account = Account.builder()
                .user(user)
                .balance(BigDecimal.valueOf(1000))
                .build();

        accountRepository.save(account);
    }
}