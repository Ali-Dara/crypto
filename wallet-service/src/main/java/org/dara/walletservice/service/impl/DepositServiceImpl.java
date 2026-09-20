package org.dara.walletservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.walletservice.dto.DepositRequest;
import org.dara.walletservice.dto.DepositResponse;
import org.dara.walletservice.exception.AssetNotFoundException;
import org.dara.walletservice.exception.WalletBalanceNotFoundException;
import org.dara.walletservice.exception.WalletNotFoundException;
import org.dara.walletservice.mapper.WalletMapper;
import org.dara.walletservice.model.Asset;
import org.dara.walletservice.model.Deposit;
import org.dara.walletservice.model.Wallet;
import org.dara.walletservice.model.WalletBalance;
import org.dara.walletservice.repository.AssetRepository;
import org.dara.walletservice.repository.DepositRepository;
import org.dara.walletservice.repository.WalletBalanceRepository;
import org.dara.walletservice.repository.WalletRepository;
import org.dara.walletservice.service.DepositService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;
    private final WalletRepository walletRepository;
    private final AssetRepository assetRepository;
    private final WalletBalanceRepository walletBalanceRepository;
    private final WalletMapper walletMapper;

    @Transactional
    @Override
    public DepositResponse deposit(UUID userUuid, DepositRequest depositRequest) {
        Wallet wallet = walletRepository
                    .findByUserUuid(userUuid)
                    .orElseThrow(() -> new WalletNotFoundException("wallet not found !!!"));

        Asset asset = assetRepository
                .findBySymbol(depositRequest.asset().toUpperCase())
                .orElseThrow(() -> new AssetNotFoundException("asset not found !!!"));

        WalletBalance walletBalance = walletBalanceRepository
                        .findByWalletIdAndAssetId(wallet.getId(), asset.getId())
                        .orElseThrow(() -> new WalletBalanceNotFoundException("wallet balance not found !!!"));

        Deposit deposit = new Deposit(wallet, asset, depositRequest.amount());
        depositRepository.save(deposit);

        walletBalance.deposit(depositRequest.amount());

        deposit.complete();

        return walletMapper.depositToDepositResponse(deposit);
    }
}
