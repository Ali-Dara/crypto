package org.dara.walletservice.service;

import org.dara.walletservice.dto.WithdrawalRequest;
import org.dara.walletservice.dto.WithdrawalResponse;

import java.util.UUID;

public interface WithdrawalOperationService {

    WithdrawalResponse process(UUID userUuid, WithdrawalRequest request, String idempotencyKey);
}
