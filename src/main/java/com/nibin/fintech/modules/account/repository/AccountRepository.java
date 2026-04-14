package com.nibin.fintech.modules.account.repository;

import com.nibin.fintech.modules.account.entity.Account;
import com.nibin.fintech.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUser(User user);
}