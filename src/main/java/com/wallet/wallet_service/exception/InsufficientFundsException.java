package com.wallet.wallet_service.exception;

import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException (UUID id) {
        super("Недостаточные средства в кошельке" + id);
    }
}
