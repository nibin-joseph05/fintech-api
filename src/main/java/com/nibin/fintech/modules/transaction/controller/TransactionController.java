package com.nibin.fintech.modules.transaction.controller;

import com.nibin.fintech.modules.transaction.dto.response.TransferResponse;
import com.nibin.fintech.modules.transaction.dto.request.TransferRequest;
import com.nibin.fintech.modules.transaction.entity.Transaction;
import com.nibin.fintech.modules.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request) {
        transactionService.transfer(request);
        return ResponseEntity.ok(new TransferResponse("Transfer successful"));
    }

    @GetMapping("/transactions/{email}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String email) {
        return ResponseEntity.ok(transactionService.getTransactions(email));
    }
}