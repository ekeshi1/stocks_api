package com.pp.stocks.price;

import com.pp.stocks.StockModel;
import com.pp.stocks.nasdaq.model.NasdaqResponse;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NasdaqStockPriceRetriever extends StockPriceRetriever {

    final  String URL = "https://api.nasdaq.com/api";

    Logger logger = LogManager.getLogger(NasdaqStockPriceRetriever.class);

    WebClient webClient;

    NasdaqStockPriceRetriever(StockPriceRepository repo, WebClient webClient){
        super(repo);
        this.webClient = webClient;
    }

    @Override
    public Set<StockPriceJpa> getLatestStockData() {
       var nasdaqResponse =
               WebClient.create(URL)
               .get()
               .uri("/quote/list-type/nasdaq100")
               .retrieve()
               .bodyToMono(NasdaqResponse.class)
                       .block();
       logger.info(nasdaqResponse.toString());

       logger.info(nasdaqResponse);
       if(nasdaqResponse.getStatus().getRCode() != 200){
           throw new RuntimeException("Couldn't retrieve latest stock data for Nasdaq!");
       }
       var stockModelSet = toStockPriceJpa(nasdaqResponse);

       return  stockModelSet;
    }

    private Set<StockModel> toStockModel(NasdaqResponse response){
        return response.getData().getData().getRows().stream()
                .map(nasdaqStock -> nasdaqStock.toDomain())
                .collect(Collectors.toSet());
    }

    private Set<StockPriceJpa> toStockPriceJpa(NasdaqResponse response){
        return response.getData().getData().getRows().stream()
                .map(nasdaqStock -> nasdaqStock.toStockPriceJpa())
                .collect(Collectors.toSet());
    }


}
