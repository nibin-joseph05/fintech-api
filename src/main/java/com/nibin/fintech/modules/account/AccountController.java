package com.nibin.fintech.modules.account;

import com.nibin.fintech.modules.account.dto.response.TransferResponse;
import com.nibin.fintech.modules.account.dto.request.TransferRequest;
import com.nibin.fintech.modules.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request) {
        accountService.transfer(request);
        return ResponseEntity.ok(new TransferResponse("Transfer successful"));
    }
}