package com.pp.index;

import com.pp.stocks.StockModel;
import com.pp.stocks.nasdaq.model.NasdaqResponse;
import com.pp.stocks.price.NasdaqStockPriceRetriever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.MathContext;
import java.util.Collections;
import java.util.Set;
import java.math.BigDecimal;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NasdaqIndexPriceCalculator implements IndexPriceCalculator {
    Logger logger = LogManager.getLogger(NasdaqIndexPriceCalculator.class);
    final  String URL = "https://api.nasdaq.com/api";
    WebClient webClient ;

   NasdaqIndexPriceCalculator(WebClient webClient){
       this.webClient = webClient;
   }

    @Override
    public BigDecimal calculateIndexPrice(Set<StockModel> composedStocks) {
        var nasdaqResponse =
                WebClient.create(URL)
                        .get()
                        .uri("/quote/NDX/info?assetclass=index")
                        .retrieve()
                        .bodyToMono(NasdaqResponse.class)
                        .block();
        logger.info(nasdaqResponse.toString());

        logger.info(nasdaqResponse);
        if(nasdaqResponse.getStatus().getRCode() != 200){
            throw new RuntimeException("Couldn't retrieve latest stock data for Nasdaq!");
        }

        return nasdaqResponse.getData().getPrimaryData().getLastSalePrice();

    }



}