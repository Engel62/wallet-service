package com.wallet.wallet_service.service;

import com.wallet.wallet_service.dto.OperationType;
import com.wallet.wallet_service.dto.WalletRequest;
import com.wallet.wallet_service.dto.WalletResponse;
import com.wallet.wallet_service.exception.InsufficientFundsException;
import com.wallet.wallet_service.exception.WalletNotFoundException;
import com.wallet.wallet_service.model.Wallet;
import com.wallet.wallet_service.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 5)
    public WalletResponse processOperation(WalletRequest request) {
        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException(request.getWalletId()));

        if (request.getOperationType() == OperationType.WITHDRAW &&
                wallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException(request.getWalletId());
        }

        BigDecimal newBalance = request.getOperationType() == OperationType.DEPOSIT
                ? wallet.getBalance().add(request.getAmount())
                : wallet.getBalance().subtract(request.getAmount());

        wallet.setBalance(newBalance);
        wallet = walletRepository.save(wallet);

        return new WalletResponse(wallet.getId(), wallet.getBalance());
    }
    public Wallet getWalletById(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
    }
    public WalletResponse getWalletBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        return new WalletResponse(wallet.getId(), wallet.getBalance());
    }
    }

