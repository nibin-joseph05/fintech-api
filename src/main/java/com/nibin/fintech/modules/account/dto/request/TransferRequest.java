package com.nibin.fintech.modules.account.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String senderEmail;
    private String receiverEmail;
    private BigDecimal amount;
}