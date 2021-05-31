package com.pp.stocks.price;

import com.pp.stocks.price.StockPriceJpa;
import com.pp.stocks.price.StockPriceRepository;
import com.pp.util.Utils;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@NoArgsConstructor
public abstract class StockPriceRetriever {

    StockPriceRepository stockPriceRepository;

    public StockPriceRetriever(StockPriceRepository stockPriceRepository){
        this.stockPriceRepository = stockPriceRepository;
    }

    abstract Set<StockPriceJpa> getLatestStockData();


    protected final Set<StockPriceJpa> updateStockPrice(Set<StockPriceJpa> latestPrices){

        var savedPricesIter = this.stockPriceRepository.saveAll(latestPrices);
        var savedPricesSet =  Set.copyOf(Utils.makeCollection(savedPricesIter));

        return savedPricesSet;
    }




}
