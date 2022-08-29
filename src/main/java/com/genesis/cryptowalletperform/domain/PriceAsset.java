package com.genesis.cryptowalletperform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PriceAsset {
    private String priceUsd = "0";
}
