package com.wallet.wallet_service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class WalletControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void concurrentRequests() throws Exception {
        IntStream.range(0, 1000).parallel().forEach(i -> {
            try {
                mockMvc.perform(post("/api/v1/wallets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"walletId\":\"...\",\"operationType\":\"DEPOSIT\",\"amount\":1}"))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("Request failed: " + e.getMessage());
            }
        });
    }
}