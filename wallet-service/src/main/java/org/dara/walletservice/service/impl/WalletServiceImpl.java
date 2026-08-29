package org.dara.walletservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dara.walletservice.model.Wallet;
import org.dara.walletservice.repository.WalletRepository;
import org.dara.walletservice.service.WalletService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    @Override
    public Wallet createWallet(UUID userUuid) {
        if(walletRepository.existsByUserUuid(userUuid)) {
            return walletRepository.findByUserUuid(userUuid).orElseThrow();
        }
        return walletRepository.save(new Wallet(userUuid));
    }

    @Override
    public Optional<Wallet> findWalletByUserUuid(UUID userUuid) {
        return walletRepository.findByUserUuid(userUuid);
    }
}
