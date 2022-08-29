package com.genesis.cryptowalletperform.services;


import com.genesis.cryptowalletperform.domain.CryptoAsset;
import com.genesis.cryptowalletperform.domain.ResultCheckPerformance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CryptoWalletServiceTest {

    @Autowired
    private CryptoWalletService service;

    @Test
    public void checkSinglePerformance() throws InterruptedException, ExecutionException {

        // Arrange
        CryptoAsset assetBtc = new CryptoAsset();
        assetBtc.setSymbol("BTC");
        assetBtc.setPrice(new BigDecimal("37870.5058"));
        assetBtc.setQuantity(new BigDecimal("0.12345"));


        // Act
        Future<ResultCheckPerformance> performance = service.check(assetBtc);

        // Assert
        while (true)
        {
            if (performance.isDone()){
                break;
            }
            Thread.sleep(1000);
        }
        assertNotNull(performance);
        assertNotNull(performance.get());

    }

    @Test
    public void checkMultiplePerformance() throws InterruptedException, ExecutionException {

        // Arrange
        CryptoAsset assetBtc = new CryptoAsset();
        assetBtc.setSymbol("BTC");
        assetBtc.setPrice(new BigDecimal("37870.5058"));
        assetBtc.setQuantity(new BigDecimal("0.12345"));

        CryptoAsset assetEth = new CryptoAsset();
        assetEth.setSymbol("ETH");
        assetEth.setPrice(new BigDecimal("2004.9774"));
        assetEth.setQuantity(new BigDecimal("4.89532"));

        CryptoAsset assetXem = new CryptoAsset();
        assetXem.setSymbol("XEM");
        assetXem.setPrice(new BigDecimal("2004.9774"));
        assetXem.setQuantity(new BigDecimal("4.89532"));

        CryptoAsset assetBSV = new CryptoAsset();
        assetBSV.setSymbol("BSV");
        assetBSV.setPrice(new BigDecimal("2004.9774"));
        assetBSV.setQuantity(new BigDecimal("4.89532"));


        // Act
        Future<ResultCheckPerformance> performance2 = service.check(assetEth);
        Future<ResultCheckPerformance> performance = service.check(assetBtc);
        Future<ResultCheckPerformance> performance3 = service.check(assetXem);
        Future<ResultCheckPerformance> performance4 = service.check(assetBSV);

        // Assert
        while (true)
        {
            if (performance.isDone() && performance2.isDone() && performance3.isDone() && performance4.isDone()){
                break;
            }
            Thread.sleep(1000);
        }
        assertNotNull(performance);
        assertNotNull(performance.get());

        assertNotNull(performance2);
        assertNotNull(performance2.get());

        assertNotNull(performance3);
        assertNotNull(performance3.get());

        assertNotNull(performance4);
        assertNotNull(performance4.get());

    }

    @Test
    public void checkWalletPerformance() throws InterruptedException {
        // Act
        List<ResultCheckPerformance> walletPerformance = service.checkWalletPerformance();

        // Assert
        assertNotNull(walletPerformance);

    }
}
