package com.pp.stocks;


import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;


    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    StockModel getStockBySymbol(String symbol){

        var _stock = this.stockRepository.findBySymbol(symbol);

        if (_stock.isEmpty()) {
            throw new IllegalArgumentException("Couldn't find this stock!");
        }

        var stock = _stock.get();

        var stockDomain = stock.toDomain();

        return stockDomain;


    }
}
