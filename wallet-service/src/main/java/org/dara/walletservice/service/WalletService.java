package org.dara.walletservice.service;

import org.dara.walletservice.model.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface WalletService {

    Wallet createWallet(UUID userUuid);
    Optional<Wallet> findWalletByUserUuid(UUID userUuid);
}
