package com.nibin.fintech.modules.transaction.repository;

import com.nibin.fintech.modules.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderEmailOrReceiverEmail(String sender, String receiver);
}