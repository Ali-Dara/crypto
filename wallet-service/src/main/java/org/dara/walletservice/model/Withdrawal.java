package org.dara.walletservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dara.walletservice.audit.AuditableEntity;
import org.dara.walletservice.model.constant.WithdrawalStatus;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.math.BigDecimal;

@Entity
@Table(name = "withdrawals",
       uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_withdrawals_idempotency_key",
                columnNames = "idempotency_key"
        )
       }
    )
@Getter
@NoArgsConstructor
@Audited
public class Withdrawal extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WithdrawalStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Asset asset;

    @Column(name="idempotency_key", nullable = false, length = 100)
    private String idempotencyKey;

    public Withdrawal(BigDecimal amount, Wallet wallet, Asset asset, String idempotencyKey) {
        this.amount = amount;
        this.wallet = wallet;
        this.asset = asset;
        this.idempotencyKey = idempotencyKey;
        this.status = WithdrawalStatus.PENDING;
    }

    public void complete(){
        status = WithdrawalStatus.COMPLETED;
    }
}
