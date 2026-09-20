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
@Table(name = "withdrawals")
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

    public Withdrawal(BigDecimal amount, Wallet wallet, Asset asset) {
        this.amount = amount;
        this.wallet = wallet;
        this.asset = asset;
        this.status = WithdrawalStatus.PENDING;
    }

    public void complete(){
        status = WithdrawalStatus.COMPLETED;
    }
}
