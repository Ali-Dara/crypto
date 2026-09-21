package org.dara.walletservice.repository;

import org.dara.walletservice.view.TransactionHistoryView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryView, String> {

    Page<TransactionHistoryView> findAllByWalletIdOrderByCreatedAtDesc(Long walletId, Pageable pageable);
}
