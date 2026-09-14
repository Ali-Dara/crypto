package org.dara.walletservice.dto;

import java.math.BigDecimal;

public record TotalWalletBalanceResponse(
        BigDecimal totalBalanceUsdt
) {}
