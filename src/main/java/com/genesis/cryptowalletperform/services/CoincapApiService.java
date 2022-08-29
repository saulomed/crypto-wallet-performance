package com.genesis.cryptowalletperform.services;

import com.genesis.cryptowalletperform.domain.CoincapAsset;
import com.genesis.cryptowalletperform.domain.PriceAsset;
import com.genesis.cryptowalletperform.domain.ResponseGetAssets;
import com.genesis.cryptowalletperform.domain.ResponseHistoryAsset;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CoincapApiService {

    public static final String HTTP_API_COINCAP_IO_V_2_ASSETS = "http://api.coincap.io/v2/assets";
    public static final String HTTP_API_COINCAP_IO_V_2_ASSETS_ID_HISTORY = "http://api.coincap.io/v2/assets/{id}/history";
    private Map<String, CoincapAsset> coincapAssetMap = null;

    @PostConstruct
    private void setup() {
        Unirest.setTimeouts(0, 0);
        Unirest.setConcurrency(3, 3);

        Unirest.setObjectMapper(new ObjectMapper() {
            private Gson gson = new GsonBuilder().disableHtmlEscaping()
                    .create();

            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                return gson.fromJson(value, valueType);
            }

            @Override
            public String writeValue(Object value) {
                return gson.toJson(value);
            }
        });
    }


    public CoincapAsset asset(String symbol) throws UnirestException {
        CoincapAsset asset = null;
        if (!symbol.isBlank()) {
            HttpResponse<ResponseGetAssets> response = Unirest.get(HTTP_API_COINCAP_IO_V_2_ASSETS)
                    .queryString("search", symbol)
                    .asObject(ResponseGetAssets.class);
            if (response.getBody().getData().length > 0) {
                List<CoincapAsset> filter = Arrays.stream(response.getBody().getData())
                        .filter(coincapAsset -> coincapAsset.getSymbol().equals(symbol))
                        .collect(Collectors.toList());
                asset = filter.size() > 0 ? filter.get(0) : null;
            }
        }

        return asset;

    }

    public PriceAsset historyAsset(String id) throws UnirestException {
        PriceAsset priceAsset = new PriceAsset();
        if (!id.isBlank()) {
            HttpResponse<ResponseHistoryAsset> response = Unirest.get(HTTP_API_COINCAP_IO_V_2_ASSETS_ID_HISTORY)
                    .routeParam("id", id)
                    .queryString("interval", "d1")
                    .queryString("start", "1617753600000")
                    .queryString("end", "1617753601000")
                    .asObject(ResponseHistoryAsset.class);
            if (response.getBody() != null && response.getBody().getData() != null && response.getBody().getData().length > 0) {
                priceAsset = response.getBody().getData()[0];

            }
        }
        return priceAsset;
    }

}
