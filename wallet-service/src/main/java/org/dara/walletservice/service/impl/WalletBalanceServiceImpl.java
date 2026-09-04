package org.dara.walletservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dara.walletservice.exception.AssetNotFoundException;
import org.dara.walletservice.exception.WalletBalanceNotFoundException;
import org.dara.walletservice.exception.WalletNotFoundException;
import org.dara.walletservice.model.Asset;
import org.dara.walletservice.model.Wallet;
import org.dara.walletservice.model.WalletBalance;
import org.dara.walletservice.repository.AssetRepository;
import org.dara.walletservice.repository.WalletBalanceRepository;
import org.dara.walletservice.repository.WalletRepository;
import org.dara.walletservice.service.WalletBalanceService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletBalanceServiceImpl implements WalletBalanceService {

    private final WalletRepository walletRepository;
    private final AssetRepository assetRepository;
    private final WalletBalanceRepository walletBalanceRepository;

    @Override
    @Transactional
    public WalletBalance findBalance(UUID userId, String assetSymbol) {

        Wallet wallet = walletRepository.findByUserUuid(userId).orElseThrow(() -> new WalletNotFoundException("Wallet Not Found"));
        Asset asset = assetRepository.findBySymbol(assetSymbol).orElseThrow(() -> new AssetNotFoundException("Asset Not Found"));
        return  walletBalanceRepository.findByWalletIdAndAssetId(wallet.getId(), asset.getId())
                                            .orElseThrow(() -> new WalletBalanceNotFoundException("Wallet balance not found"));
    }
}
