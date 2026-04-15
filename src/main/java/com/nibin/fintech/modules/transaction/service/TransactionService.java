package com.nibin.fintech.modules.transaction.service;

import com.nibin.fintech.core.exception.ApiException;
import com.nibin.fintech.modules.transaction.dto.request.TransferRequest;
import com.nibin.fintech.modules.account.entity.Account;
import com.nibin.fintech.modules.account.repository.AccountRepository;
import com.nibin.fintech.modules.transaction.entity.Transaction;
import com.nibin.fintech.modules.transaction.repository.TransactionRepository;
import com.nibin.fintech.modules.user.entity.User;
import com.nibin.fintech.modules.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

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

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Invalid amount");
        }

        if (request.getSenderEmail().equals(request.getReceiverEmail())) {
            throw new ApiException("Cannot transfer to same account");
        }

        if (senderAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new ApiException("Insufficient balance");
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(request.getAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(request.getAmount()));

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        transactionRepository.save(
                Transaction.builder()
                        .senderEmail(sender.getEmail())
                        .receiverEmail(receiver.getEmail())
                        .amount(request.getAmount())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    public List<Transaction> getTransactions(String email) {
        return transactionRepository.findBySenderEmailOrReceiverEmail(email, email);
    }
}
