package org.dara.walletservice.repository;

import org.dara.walletservice.model.WalletBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletBalanceRepository extends JpaRepository<WalletBalance, UUID> {

    Optional<WalletBalance> findByWalletId(Long walletId);
}
