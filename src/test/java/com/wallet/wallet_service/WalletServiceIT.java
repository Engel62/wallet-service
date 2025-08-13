package com.wallet.wallet_service;

import com.wallet.wallet_service.model.Wallet;
import com.wallet.wallet_service.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class WalletServiceIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private WalletRepository walletRepository;

    @Test
    void shouldSaveWalletToDatabase() {
        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setBalance(BigDecimal.valueOf(1000));

        Wallet saved = walletRepository.save(wallet);

        assertNotNull(saved.getId());
        assertEquals(0, saved.getBalance().compareTo(BigDecimal.valueOf(1000)));
    }
}