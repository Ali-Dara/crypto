package org.dara.walletservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.walletservice.dto.WithdrawalRequest;
import org.dara.walletservice.dto.WithdrawalResponse;
import org.dara.walletservice.exception.AssetNotFoundException;
import org.dara.walletservice.exception.InsufficientBalanceException;
import org.dara.walletservice.exception.WalletBalanceNotFoundException;
import org.dara.walletservice.mapper.WalletMapper;
import org.dara.walletservice.model.Asset;
import org.dara.walletservice.model.Wallet;
import org.dara.walletservice.model.WalletBalance;
import org.dara.walletservice.model.Withdrawal;
import org.dara.walletservice.repository.AssetRepository;
import org.dara.walletservice.repository.WalletBalanceRepository;
import org.dara.walletservice.repository.WalletRepository;
import org.dara.walletservice.repository.WithdrawalRepository;
import org.dara.walletservice.service.WithdrawalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;
    private final WalletRepository walletRepository;
    private final AssetRepository assetRepository;
    private final WalletBalanceRepository walletBalanceRepository;
    private final WalletMapper walletMapper;

    @Transactional
    @Override
    public WithdrawalResponse withdraw(UUID userUuid, WithdrawalRequest request) {

        Wallet wallet = walletRepository.findByUserUuid(userUuid)
                .orElseThrow(() -> new WalletBalanceNotFoundException("Wallet not found"));

        Asset asset = assetRepository.findBySymbol(request.assetSymbol())
                .orElseThrow(() -> new AssetNotFoundException("Asset not found"));

        WalletBalance walletBalance = walletBalanceRepository.findByWalletIdAndAssetIdForUpdate(wallet.getId(), asset.getId())
                .orElseThrow(() -> new WalletBalanceNotFoundException("WalletBalance not found"));

        walletBalance.withdraw(request.amount());

        Withdrawal withdrawal = new Withdrawal(request.amount(), wallet, asset);
        withdrawal.complete();
        withdrawalRepository.save(withdrawal);

        return walletMapper.withdrawalToWithdrawalResponse(withdrawal);
    }
}
