package com.genesis.cryptowalletperform.services;

import com.genesis.cryptowalletperform.domain.CoincapAsset;
import com.genesis.cryptowalletperform.domain.CryptoAsset;
import com.genesis.cryptowalletperform.domain.ResultCheckPerformance;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CryptoWalletService {

    @Autowired
    private CoincapApiService coincapApiService;

    @Autowired
    private LoadCsvFileService loadCsvFileService;

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);


    public Future<ResultCheckPerformance> check(CryptoAsset asset) {
        return executor.submit(() -> {
            ResultCheckPerformance performance = null;
            try {
                System.out.println(String.format("Submitted request %s at %s", asset.getSymbol(), LocalTime.now()));
                CoincapAsset coincapAsset = coincapApiService.asset(asset.getSymbol());
                BigDecimal actualValue = new BigDecimal(coincapApiService.historyAsset(coincapAsset.getId()).getPriceUsd());
                if (coincapAsset != null) {
                    performance = new ResultCheckPerformance();

                    BigDecimal actualTotal = asset.getQuantity().multiply(actualValue).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal total = asset.getQuantity().multiply(asset.getPrice()).setScale(2, RoundingMode.HALF_UP);
                    performance.setActualTotal(actualTotal);
                    performance.setPerformance(actualTotal.min(total).setScale(2, RoundingMode.HALF_UP));
                    performance.setPercentagePerformance(actualTotal.divide(total, 2, RoundingMode.HALF_UP));
                    performance.setAsset(asset);
                    performance.setActualValue(actualValue);

                    System.out.println(String.format("Asset: %s, id: %s totalPaid: %s actualTotal: %s performance: %s percentage: %s quantity: %s price: %s actualPrice: %s",
                            asset.getSymbol(),coincapAsset.getId() , total, actualTotal,performance.getPerformance(), performance.getPercentagePerformance(),
                            asset.getQuantity(), asset.getPrice(), actualValue));
                }

            } catch (UnirestException e) {
                e.printStackTrace();
            }
            return performance;
        });
    }


    public List<ResultCheckPerformance> checkWalletPerformance() throws InterruptedException {
        System.out.println(String.format("Now is %s", LocalTime.now().toString()));
        CompletionService<ResultCheckPerformance> completionService =
                new ExecutorCompletionService<>(executor);

        List<CryptoAsset> wallet = loadCsvFileService.loadCsv();
        Set<ResultCheckPerformance> walletPerformanceResult = new HashSet<>();
        List<Future<ResultCheckPerformance>> controlResponse = new ArrayList<>();
        wallet.forEach(asset -> controlResponse.add(check(asset)));

        AtomicBoolean completed = new AtomicBoolean(false);
        while (!completed.get()) {
            controlResponse.forEach(future -> {
                if (future.isDone()) {
                    completed.set(true);
                    try {
                        walletPerformanceResult.add(future.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else
                    completed.set(false);
            });
        }
        List<ResultCheckPerformance> result = new ArrayList<>(walletPerformanceResult);
        result.sort(Comparator.comparing(ResultCheckPerformance::getPerformance));

        BigDecimal total = result.stream().map(v -> v.getActualTotal()).reduce(BigDecimal.ZERO, BigDecimal ::add);

        System.out.println(String.format("total=%s,best_asset=%s,best_performance=%s,worst_asset=%s,worst_performance=%s",
                total, result.get(result.size()-1).getAsset().getSymbol(), result.get(result.size()-1).getPercentagePerformance(),
                result.get(0).getAsset().getSymbol(), result.get(0).getPercentagePerformance()));

        return result;
    }

}
