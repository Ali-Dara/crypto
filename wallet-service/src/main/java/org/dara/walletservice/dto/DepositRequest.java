package org.dara.walletservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DepositRequest(
        @NotBlank
        String asset,

        @NotNull
        @DecimalMin(value = "0.00000001")
        BigDecimal amount
) {}
