package com.wallet.wallet_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.UUID;
//запрос на операцию
@Data
public class WalletRequest {
    @NotNull
    private UUID walletId;
    @NotNull
    private OperationType operationType;
    @DecimalMin("0,01")
    private BigDecimal amount;

}

