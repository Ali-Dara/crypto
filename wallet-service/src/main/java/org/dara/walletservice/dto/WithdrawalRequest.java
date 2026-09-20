package org.dara.walletservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WithdrawalRequest(
        @NotBlank
        String assetSymbol,

        @NotNull
        @DecimalMin(value = "0.00000001")
        BigDecimal amount
) {}
