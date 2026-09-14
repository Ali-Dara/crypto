package org.dara.walletservice.dto;

import java.math.BigDecimal;

public record DepositResponse (
        String asset,
        BigDecimal amount,
        String status
){}
