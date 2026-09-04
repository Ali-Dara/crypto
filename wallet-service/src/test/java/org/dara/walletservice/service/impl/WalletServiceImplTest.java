package org.dara.walletservice.service.impl;

import org.dara.walletservice.model.Asset;
import org.dara.walletservice.model.Wallet;
import org.dara.walletservice.model.WalletBalance;
import org.dara.walletservice.model.constant.AssetType;
import org.dara.walletservice.repository.AssetRepository;
import org.dara.walletservice.repository.WalletBalanceRepository;
import org.dara.walletservice.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;
    @Mock
    private WalletBalanceRepository walletBalanceRepository;
    @Mock
    private AssetRepository assetRepository;
    @InjectMocks
    private WalletServiceImpl walletServiceImpl;

    @Test
    void createWallet_shouldReturnExistingWallet_whenWalletAlreadyExists() {
        UUID userUuid = UUID.randomUUID();
        Wallet existingWallet = new Wallet(userUuid);
        when(walletRepository.findByUserUuid(userUuid)).thenReturn(Optional.of(existingWallet));
        Wallet result = walletServiceImpl.createWallet(userUuid);
        assertSame(existingWallet, result);
        verify(walletRepository).findByUserUuid(userUuid);
        verify(walletRepository, never()).save(any(Wallet.class));
    }

    @Test
    void createWallet_shouldCreateWalletWithActiveAssets_whenWalletDoesNotExist() {
        UUID userUuid = UUID.randomUUID();

        Asset btc = new Asset();
        btc.setName("Bitcoin");
        btc.setSymbol("BTC");
        btc.setType(AssetType.CRYPTO);
        btc.setActive(true);

        Asset usdt = new Asset();
        usdt.setName("Tether");
        usdt.setSymbol("USDT");
        usdt.setType(AssetType.CRYPTO);
        usdt.setActive(true);

        when(walletRepository.findByUserUuid(userUuid)).thenReturn(Optional.empty());

        Wallet savedWallet = new Wallet(userUuid);
        //when(walletRepository.save(any(Wallet.class))).thenReturn(savedWallet);
        when(assetRepository.findByActiveTrue()).thenReturn(List.of(btc, usdt));

        Wallet result = walletServiceImpl.createWallet(userUuid);

        assertNull(result);
        assertNotNull(result);
        ArgumentCaptor<WalletBalance> balanceCaptor = ArgumentCaptor.forClass(WalletBalance.class);
        List<WalletBalance> capturedBalances = balanceCaptor.getAllValues();
        verify(walletRepository).findByUserUuid(userUuid);
        verify(walletRepository).save(any(Wallet.class));
        verify(assetRepository).findByActiveTrue();
        verify(walletBalanceRepository, times(2)).save(any(WalletBalance.class));
        assertSame(btc, capturedBalances.get(0).getAsset());

        assertSame(usdt, capturedBalances.get(1).getAsset());
    }
}
