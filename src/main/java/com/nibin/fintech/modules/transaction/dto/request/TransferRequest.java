package com.nibin.fintech.modules.transaction.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    @NotBlank
    private String senderEmail;

    @NotBlank
    private String receiverEmail;

    @NotNull
    private BigDecimal amount;
}