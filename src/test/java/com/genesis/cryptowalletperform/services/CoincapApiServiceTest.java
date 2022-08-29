package com.genesis.cryptowalletperform.services;

import com.genesis.cryptowalletperform.domain.CoincapAsset;
import com.genesis.cryptowalletperform.domain.PriceAsset;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CoincapApiServiceTest {

    @Autowired
    private CoincapApiService service;


    @Test
    public void getAssetSuccessTest() throws UnirestException {
        // Act
        CoincapAsset asset = service.asset("BTC");

        // Assert
        assertNotNull(asset);
        assertTrue(asset.getSymbol().equals("BTC"));
    }

    @Test
    public void getAssetFailedTest() throws UnirestException {
        // Act
        CoincapAsset asset = service.asset("B");

        // Assert
        assertNull(asset);
    }

    @Test
    public void getHistoryPrice() throws UnirestException {
        // Act
        PriceAsset priceAsset = service.historyAsset("bitcoin");

        // Assert
        assertNotNull(priceAsset);
        assertNotNull(priceAsset.getPriceUsd());
    }

    @Test
    public void getHistoryPriceWithNoHistory() throws UnirestException {
        // Act
        PriceAsset priceAsset = service.historyAsset("near-protocol");

        // Assert
        assertNotNull(priceAsset);
        assertEquals("0",priceAsset.getPriceUsd());
    }
}
