package org.dara.walletservice.service;

import org.dara.walletservice.model.Wallet;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface WalletService {

    Wallet createWallet(UUID userUuid);
    Optional<Wallet> findWalletByUserUuid(UUID userUuid);
    void deposit(UUID userUuid, String symbol, BigDecimal amount);
}
