package com.wallet.wallet_service.controller;

import com.wallet.wallet_service.dto.WalletRequest;
import com.wallet.wallet_service.dto.WalletResponse;
import com.wallet.wallet_service.exception.ErrorResponse;
import com.wallet.wallet_service.exception.InsufficientFundsException;
import com.wallet.wallet_service.exception.WalletNotFoundException;
import com.wallet.wallet_service.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<?> processOperation(@RequestBody @Valid WalletRequest request) {
        try {
            WalletResponse response = walletService.processOperation(request);
            return ResponseEntity.ok(response);
        } catch (WalletNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND, "WALLET_NOT_FOUND", ex.getMessage()));
        } catch (InsufficientFundsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST, "INSUFFICIENT_FUNDS", ex.getMessage()));
        }
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<?> getBalance(@PathVariable UUID walletId) {
        try {
            WalletResponse response = walletService.getWalletBalance(walletId);
            return ResponseEntity.ok(response);
        } catch (WalletNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND, "WALLET_NOT_FOUND", ex.getMessage()));
        }
    }
}