package org.dara.walletservice.dto;

import java.math.BigDecimal;

public record WalletBalanceResponse(
        Long id,
        String assetSymbol,
        BigDecimal availableBalance,
        BigDecimal lockedBalance
) {}
