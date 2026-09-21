package org.dara.walletservice.repository;

import org.dara.walletservice.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

    List<Deposit> findAllByWalletId(Long walletId);
}
