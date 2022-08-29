package com.genesis.cryptowalletperform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CoincapAsset {

    private String id;

    private String symbol;

    private String name;

    private String priceUsd;

}
