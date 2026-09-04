package org.dara.walletservice.exception;

public class WalletBalanceNotFoundException extends RuntimeException {
    public WalletBalanceNotFoundException(String message) {
        super(message);
    }
}
