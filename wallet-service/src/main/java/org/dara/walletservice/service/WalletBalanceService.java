package org.dara.walletservice.service;

import org.dara.walletservice.model.WalletBalance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletBalanceService {

    WalletBalance findBalance(UUID userId, String assetSymbol);

    List<WalletBalance> findAllBalances(UUID userId);

}
