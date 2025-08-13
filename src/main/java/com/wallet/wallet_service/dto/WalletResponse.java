package com.wallet.wallet_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;
//ответ с балансом
@AllArgsConstructor
@Data
public class WalletResponse {
    private UUID walletId;
    private BigDecimal balance;
}
