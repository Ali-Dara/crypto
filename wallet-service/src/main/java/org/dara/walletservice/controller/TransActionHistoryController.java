package org.dara.walletservice.controller;

import lombok.RequiredArgsConstructor;
import org.dara.cryptosecurity.model.CurrentUser;
import org.dara.walletservice.dto.TransactionHistoryPageResponse;
import org.dara.walletservice.dto.TransactionHistoryResponse;
import org.dara.walletservice.service.TransActionHistoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class TransActionHistoryController {

    private final TransActionHistoryService transActionHistoryService;

    @GetMapping("/transactions")
    public ResponseEntity<TransactionHistoryPageResponse> getTransactions(@AuthenticationPrincipal CurrentUser currentUser, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(transActionHistoryService.getTransactions(currentUser.userUuid(), pageable));
    }
}
