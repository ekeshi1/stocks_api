package com.pp.stocks.price;


import com.pp.config.StockPriceSchedulerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StockScheduledService {

    StockPriceRetriever stockPriceRetriever;
    Logger logger = LogManager.getLogger(StockScheduledService.class);

    public StockScheduledService(NasdaqStockPriceRetriever stockPriceRetriever){
        this.stockPriceRetriever = stockPriceRetriever;
    }

    @Scheduled(fixedRateString = "${stock.price.nasdaq.scraping_time_millis}", initialDelay = 0L)
    public void updateStockPrice(){
        logger.info("Starting scheduled stock price update");
        var latestStockPrice = this.stockPriceRetriever.getLatestStockData();
        this.stockPriceRetriever.updateStockPrice(latestStockPrice);
        logger.info("Finished scheduled stock price update");
    }

}
