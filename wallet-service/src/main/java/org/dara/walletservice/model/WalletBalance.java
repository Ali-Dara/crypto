package org.dara.walletservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dara.walletservice.exception.InsufficientBalanceException;
import org.dara.walletservice.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_balances")
@Getter
@NoArgsConstructor
public class WalletBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal availableBalance;

    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal  lockedBalance;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id",nullable = false)
    private Asset asset;

    public WalletBalance(Wallet wallet, Asset asset) {
        this.wallet = wallet;
        this.asset = asset;
        this.availableBalance = BigDecimal.ZERO;
        this.lockedBalance = BigDecimal.ZERO;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public void validateAmount(BigDecimal amount){
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidAmountException("Amount must be greater than zero");

    }

    public void lock(BigDecimal amount){
        validateAmount(amount);
        if(availableBalance.compareTo(amount) < 0)
            throw new InsufficientBalanceException("Insufficient available balance to lock");

        availableBalance = availableBalance.subtract(amount);
        lockedBalance = lockedBalance.add(amount);
    }

    public void unlock(BigDecimal amount){
        validateAmount(amount);
        if(lockedBalance.compareTo(amount) < 0)
            throw new InsufficientBalanceException("Insufficient locked balance");

        lockedBalance = lockedBalance.subtract(amount);
        availableBalance = availableBalance.add(amount);
    }

    public void deposit(BigDecimal amount){
        validateAmount(amount);
        availableBalance = availableBalance.add(amount);
    }

    public void withdraw(BigDecimal amount){
        validateAmount(amount);
        if(availableBalance.compareTo(amount) < 0)
            throw new InsufficientBalanceException("Insufficient available balance");
        availableBalance = availableBalance.subtract(amount);
    }

}
