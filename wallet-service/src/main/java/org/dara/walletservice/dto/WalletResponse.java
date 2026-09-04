package org.dara.walletservice.dto;

import java.util.List;
import java.util.UUID;

public record WalletResponse(
        Long id,
        UUID userUuid,
        List<WalletBalanceResponse> walletBalances
) {}
