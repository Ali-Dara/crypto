package org.dara.walletservice.model;

import org.dara.walletservice.exception.InsufficientBalanceException;
import org.dara.walletservice.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WalletBalanceTest {

    @Test
    void deposit_shouldIncreaseAvailableBalance(){
        WalletBalance wb = new WalletBalance(null, null);
        wb.deposit(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), wb.getAvailableBalance());
    }

    @Test
    void withdraw_shouldDecreaseAvailableBalance(){
        WalletBalance wb = new WalletBalance(null, null);
        wb.deposit(BigDecimal.valueOf(100));
        wb.withdraw(BigDecimal.valueOf(30));
        assertEquals(BigDecimal.valueOf(70), wb.getAvailableBalance());
    }

    @Test
    void withdraw_shouldThrowException_whenBalanceIsInsufficient(){
        WalletBalance wb = new WalletBalance(null, null);
        wb.deposit(BigDecimal.valueOf(100));
        assertThrows(InsufficientBalanceException.class, () -> wb.withdraw(BigDecimal.valueOf(150)));
    }

    @Test
    void lock_shouldMoveBalanceFromAvailableToLocked(){
        WalletBalance wb = new WalletBalance(null, null);
        wb.deposit(BigDecimal.valueOf(100));
        wb.lock(BigDecimal.valueOf(40));
        assertEquals(BigDecimal.valueOf(60), wb.getAvailableBalance());
        assertEquals(BigDecimal.valueOf(40), wb.getLockedBalance());
    }

    @Test
    void unlock_shouldMoveBalanceFromLockedToAvailable(){
        WalletBalance wb = new WalletBalance(null, null);
        wb.deposit(BigDecimal.valueOf(100));
        wb.lock(BigDecimal.valueOf(40));
        wb.unlock(BigDecimal.valueOf(20));
        assertEquals(BigDecimal.valueOf(80), wb.getAvailableBalance());
        assertEquals(BigDecimal.valueOf(20), wb.getLockedBalance());
    }

    @Test
    void deposit_shouldThrowException_whenAmountIsZero() {
        WalletBalance balance = new WalletBalance(null, null);
        assertThrows(InvalidAmountException.class, () -> balance.deposit(BigDecimal.ZERO));
    }

    @Test
    void deposit_shouldThrowException_whenAmountIsNegative() {
        WalletBalance balance = new WalletBalance(null, null);
        assertThrows(InvalidAmountException.class, () -> balance.deposit(BigDecimal.valueOf(-10))
        );
    }
}
