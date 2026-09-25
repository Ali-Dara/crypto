package org.dara.walletservice.dto;

import java.math.BigDecimal;

public record WithdrawalResponse(

        String asset,
        BigDecimal amount,
        String status,
        String idempotencyKey
) {}
