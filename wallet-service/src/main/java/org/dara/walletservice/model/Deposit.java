package org.dara.walletservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dara.walletservice.audit.AuditableEntity;
import org.dara.walletservice.model.constant.DepositStatus;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.math.BigDecimal;

@Entity
@Table(name = "deposits")
@Getter
@NoArgsConstructor
@Audited
public class Deposit extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DepositStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Asset asset;

    public Deposit(Wallet wallet, Asset asset, BigDecimal amount) {
        this.wallet = wallet;
        this.asset = asset;
        this.amount = amount;
        this.status = DepositStatus.PENDING;
    }

    public void complete(){
        status = DepositStatus.COMPLETED;
    }
}
