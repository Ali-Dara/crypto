package org.dara.walletservice.service;

import org.dara.walletservice.dto.WithdrawalRequest;
import org.dara.walletservice.dto.WithdrawalResponse;

import java.util.UUID;

public interface WithdrawalService {

    WithdrawalResponse withdraw(UUID userUuid, WithdrawalRequest request);
}
