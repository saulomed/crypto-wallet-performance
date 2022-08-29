package com.genesis.cryptowalletperform;

import com.genesis.cryptowalletperform.services.CryptoWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CryptoWalletPerformApplicationStartupRunner implements CommandLineRunner {

    @Autowired
    private CryptoWalletService service;

    @Override
    public void run(String... args) throws Exception {
        service.checkWalletPerformance();
    }
}
