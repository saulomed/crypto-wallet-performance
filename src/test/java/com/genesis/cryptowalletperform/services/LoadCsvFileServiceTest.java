package com.genesis.cryptowalletperform.services;

import com.genesis.cryptowalletperform.domain.CryptoAsset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LoadCsvFileServiceTest {

    @Autowired
    private LoadCsvFileService service;

    @Test
    public void loadCsvTest()
    {
        // Arrange
        CryptoAsset btcAsset = new CryptoAsset("BTC", new BigDecimal("0.12345"), new BigDecimal("37870.5058"));

        // Act
        List<CryptoAsset> resultList = service.loadCsv();

        // Assert
        assertTrue(resultList.contains(btcAsset));
    }
}
