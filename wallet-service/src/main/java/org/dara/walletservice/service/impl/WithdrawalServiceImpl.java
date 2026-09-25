package org.dara.walletservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.walletservice.dto.WithdrawalRequest;
import org.dara.walletservice.dto.WithdrawalResponse;
import org.dara.walletservice.mapper.WalletMapper;
import org.dara.walletservice.model.Withdrawal;
import org.dara.walletservice.repository.WithdrawalRepository;
import org.dara.walletservice.service.WithdrawalOperationService;
import org.dara.walletservice.service.WithdrawalService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;
    private final WalletMapper walletMapper;
    private final WithdrawalOperationService withdrawalOperationService;

    @Override
    public WithdrawalResponse withdraw(UUID userUuid, WithdrawalRequest request, String idempotencyKey) {
        Withdrawal existingWithdrawal  = withdrawalRepository.findByIdempotencyKey(idempotencyKey).orElse(null);
        if (existingWithdrawal != null)
            return walletMapper.withdrawalToWithdrawalResponse(existingWithdrawal);

        try{
            return withdrawalOperationService.process(userUuid, request, idempotencyKey);
        }catch (DataIntegrityViolationException ex){
            Withdrawal withdrawal   = withdrawalRepository.findByIdempotencyKey(idempotencyKey).orElse(null);
            return walletMapper.withdrawalToWithdrawalResponse(withdrawal);
        }
    }
}
