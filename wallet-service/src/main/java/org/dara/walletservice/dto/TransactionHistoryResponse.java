package org.dara.walletservice.dto;

import org.dara.walletservice.model.constant.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionHistoryResponse(
   String transactionId,
   TransactionType type,
   String asset,
   BigDecimal amount,
   String status,
   LocalDateTime createdAt
) {}
