package com.nibin.fintech.modules.account.service;

import com.nibin.fintech.core.exception.ApiException;
import com.nibin.fintech.modules.account.dto.request.TransferRequest;
import com.nibin.fintech.modules.account.entity.Account;
import com.nibin.fintech.modules.account.repository.AccountRepository;
import com.nibin.fintech.modules.user.entity.User;
import com.nibin.fintech.modules.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    @Transactional
    public void transfer(TransferRequest request) {

        User sender = userRepository.findByEmail(request.getSenderEmail())
                .orElseThrow(() -> new ApiException("Sender not found"));

        User receiver = userRepository.findByEmail(request.getReceiverEmail())
                .orElseThrow(() -> new ApiException("Receiver not found"));

        Account senderAccount = accountRepository.findByUser(sender)
                .orElseThrow(() -> new ApiException("Sender account not found"));

        Account receiverAccount = accountRepository.findByUser(receiver)
                .orElseThrow(() -> new ApiException("Receiver account not found"));

        if (senderAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(request.getAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(request.getAmount()));

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
    }
}
