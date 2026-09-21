package org.dara.walletservice.service;

import org.dara.walletservice.dto.TransactionHistoryPageResponse;
import org.dara.walletservice.dto.TransactionHistoryResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TransActionHistoryService {

    TransactionHistoryPageResponse getTransactions(UUID userId, Pageable pageable);
}
