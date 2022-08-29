package com.genesis.cryptowalletperform.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Comparator;

@Data
public class ResultCheckPerformance implements Comparator {

    private BigDecimal actualTotal;

    private BigDecimal valuePaid;

    private BigDecimal actualValue;

    private BigDecimal performance;

    private BigDecimal percentagePerformance;

    private CryptoAsset asset;

    @Override
    public int compare(Object o1, Object o2) {
        ResultCheckPerformance result1 = (ResultCheckPerformance)o1;
        ResultCheckPerformance result2 = (ResultCheckPerformance)o2;

        return result1.performance.compareTo(result2.performance);
    }
}
