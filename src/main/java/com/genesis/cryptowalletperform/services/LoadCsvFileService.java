package com.genesis.cryptowalletperform.services;

import com.genesis.cryptowalletperform.domain.CryptoAsset;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class LoadCsvFileService {
    public List<CryptoAsset> loadCsv(){

        List<CryptoAsset> cryptoAssetList = null;

        try(Reader reader = Files.newBufferedReader(Path.of(this.getClass().getClassLoader().getResource("cryptoAssets.csv").getPath()))){
            CsvToBean<CryptoAsset> csvToBean = new CsvToBeanBuilder<CryptoAsset>(reader)
                    .withType(CryptoAsset.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            cryptoAssetList = csvToBean.parse();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cryptoAssetList;
    }
}
