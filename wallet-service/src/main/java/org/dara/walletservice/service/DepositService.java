package org.dara.walletservice.service;

import org.dara.walletservice.dto.DepositRequest;
import org.dara.walletservice.dto.DepositResponse;

import java.util.UUID;

public interface DepositService {
    DepositResponse deposit(UUID userUuid, DepositRequest depositRequest);
}
