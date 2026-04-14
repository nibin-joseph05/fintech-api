package com.nibin.fintech.modules.otp.repository;

import com.nibin.fintech.modules.otp.entity.Otp;
import com.nibin.fintech.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByUser(User user);

    void deleteByUser(User user);
}