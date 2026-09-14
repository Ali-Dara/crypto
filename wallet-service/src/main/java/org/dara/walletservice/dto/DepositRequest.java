package org.dara.walletservice.dto;

import java.math.BigDecimal;

public record DepositRequest(
        String asset,
        BigDecimal amount
) {}
