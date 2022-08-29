package com.genesis.cryptowalletperform.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CryptoAsset {

    @CsvBindByName
    private String symbol;

    @CsvBindByName
    private BigDecimal quantity;

    @CsvBindByName
    private BigDecimal price;
}
