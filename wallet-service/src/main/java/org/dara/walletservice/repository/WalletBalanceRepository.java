package org.dara.walletservice.repository;

import jakarta.persistence.LockModeType;
import org.dara.walletservice.model.WalletBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletBalanceRepository extends JpaRepository<WalletBalance, Long> {

    Optional<WalletBalance> findByWalletIdAndAssetId(Long walletId, Long assetId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    Select wb 
    From WalletBalance wb
    Where wb.wallet.id =:walletId and
          wb.asset.id =:assetId
    """)
    Optional<WalletBalance> findByWalletIdAndAssetIdForUpdate(@Param("walletId") Long walletId, @Param("assetId") Long assetId);


}
