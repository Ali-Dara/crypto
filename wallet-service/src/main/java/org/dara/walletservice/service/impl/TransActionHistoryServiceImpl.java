package org.dara.walletservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.walletservice.dto.TransactionHistoryPageResponse;
import org.dara.walletservice.dto.TransactionHistoryResponse;
import org.dara.walletservice.exception.WalletNotFoundException;
import org.dara.walletservice.model.Wallet;
import org.dara.walletservice.repository.TransactionHistoryRepository;
import org.dara.walletservice.repository.WalletRepository;
import org.dara.walletservice.service.TransActionHistoryService;
import org.dara.walletservice.view.TransactionHistoryView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransActionHistoryServiceImpl implements TransActionHistoryService {

    private final WalletRepository walletRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public TransactionHistoryPageResponse getTransactions(UUID userId, Pageable pageable) {

        Wallet wallet = walletRepository.findByUserUuid(userId).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        Page<TransactionHistoryView> transactions =
                transactionHistoryRepository
                        .findAllByWalletIdOrderByCreatedAtDesc(
                                wallet.getId(),
                                pageable
                        );

        List<TransactionHistoryResponse> content =
                transactions.getContent()
                        .stream()
                        .map(transaction -> new TransactionHistoryResponse(
                                transaction.getTransactionId(),
                                transaction.getType(),
                                transaction.getAsset(),
                                transaction.getAmount(),
                                transaction.getStatus(),
                                transaction.getCreatedAt()
                        ))
                        .toList();

        return new TransactionHistoryPageResponse(
                content,
                transactions.getNumber(),
                transactions.getSize(),
                transactions.getTotalElements(),
                transactions.getTotalPages()
        );
    }
}
