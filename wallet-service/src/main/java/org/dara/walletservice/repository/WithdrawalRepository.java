package org.dara.walletservice.repository;

import org.dara.walletservice.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

    List<Withdrawal> findAllByWalletId(Long walletId);

    Optional<Withdrawal> findByIdempotencyKey(String idempotencyKey);
}
