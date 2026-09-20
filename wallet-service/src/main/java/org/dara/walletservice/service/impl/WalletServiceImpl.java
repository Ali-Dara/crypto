package org.dara.walletservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dara.walletservice.exception.WalletNotFoundException;
import org.dara.walletservice.grpcClient.MarketGrpcClient;
import org.dara.walletservice.model.Asset;
import org.dara.walletservice.model.Wallet;
import org.dara.walletservice.model.WalletBalance;
import org.dara.walletservice.repository.AssetRepository;
import org.dara.walletservice.repository.WalletBalanceRepository;
import org.dara.walletservice.repository.WalletRepository;
import org.dara.walletservice.service.WalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final AssetRepository assetRepository;
    private final WalletBalanceRepository walletBalanceRepository;
    private final MarketGrpcClient marketGrpcClient;

    @Transactional
    @Override
    public Wallet createWallet(UUID userUuid) {

        System.out.println("========== CREATE WALLET START ==========");

        Optional<Wallet> existingWallet = walletRepository.findByUserUuid(userUuid);

        System.out.println("Wallet lookup done");

        if (existingWallet.isPresent())
            return existingWallet.get();
        Wallet wallet = new Wallet(userUuid);
        Wallet savedWallet = walletRepository.save(wallet);

        System.out.println("Wallet saved: " + savedWallet.getId());
        System.out.println("Before asset query");

        List<Asset> assets = assetRepository.findByActiveTrue();
        System.out.println("Assets loaded: " + assets.size());
        System.out.println("ACTIVE ASSETS = " + assets.size());

        for (Asset asset : assets) {
            WalletBalance walletBalance = new WalletBalance(savedWallet, asset);
            walletBalanceRepository.save(walletBalance);

            System.out.println("Balance saved for: "
                    + asset.getSymbol());
        }

        System.out.println("========== CREATE WALLET END ==========");
        return savedWallet;
    }

    @Override
    public Optional<Wallet> findWalletByUserUuid(UUID userUuid) {
        return walletRepository.findByUserUuid(userUuid);
    }

    @Override
    public void deposit(UUID userUuid, String symbol, BigDecimal amount) {

    }

    @Override
    public BigDecimal calculateTotalBalance(UUID userId) {
        Wallet wallet = walletRepository.findByUserUuid(userId).orElseThrow(() -> new WalletNotFoundException("Wallet Not Found"));
        List<String> symbols = wallet.getWalletBalances()
                .stream()
                .map(balance -> balance.getAsset().getSymbol())
                .distinct()
                .toList();

        Map<String, BigDecimal> prices = marketGrpcClient.getPrices(symbols);

        BigDecimal total = wallet.getWalletBalances()
                .stream()
                .map(balance -> {
                    String symbol = balance.getAsset().getSymbol();
                    BigDecimal amount = balance.getAvailableBalance().add(balance.getLockedBalance());
                    BigDecimal price = prices.get(symbol);
                    if (price == null)
                        return BigDecimal.ZERO;
                    return amount.multiply(price);
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }
}
