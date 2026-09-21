package org.dara.walletservice.view;

import jakarta.persistence.*;
import lombok.Getter;
import org.dara.walletservice.model.constant.TransactionType;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "wallet_transaction_history")
@Getter
public class TransactionHistoryView {

    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "wallet_id", nullable = false)
    private Long walletId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private String asset;

    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
