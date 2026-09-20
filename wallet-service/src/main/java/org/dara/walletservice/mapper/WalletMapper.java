package org.dara.walletservice.mapper;

import org.dara.walletservice.dto.DepositResponse;
import org.dara.walletservice.dto.WalletBalanceResponse;
import org.dara.walletservice.dto.WalletResponse;
import org.dara.walletservice.dto.WithdrawalResponse;
import org.dara.walletservice.model.Deposit;
import org.dara.walletservice.model.Wallet;
import org.dara.walletservice.model.WalletBalance;
import org.dara.walletservice.model.Withdrawal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletResponse walletToWalletResponse(Wallet wallet);

    @Mapping(source = "asset.symbol", target = "assetSymbol")
    WalletBalanceResponse walletBalanceToWalletBalanceResponse(WalletBalance walletBalance);

    List<WalletBalanceResponse> walletBalancesToWalletBalanceResponse(List<WalletBalance> walletBalances);

    @Mapping(source = "asset.symbol", target = "asset")
    DepositResponse depositToDepositResponse(Deposit deposit);

    @Mapping(source = "asset.symbol", target = "asset")
    WithdrawalResponse withdrawalToWithdrawalResponse(Withdrawal withdrawal);
}
