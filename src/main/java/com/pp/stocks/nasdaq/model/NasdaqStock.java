package com.pp.stocks.nasdaq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pp.stocks.StockJpa;
import com.pp.stocks.StockModel;
import com.pp.stocks.price.StockPriceJpa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NasdaqStock {

    private String symbol;

    private String companyName;

    private String companyWebsite;

    @JsonDeserialize(using = FlexibleBigDecimalDeserializer.class)
    private BigDecimal marketCap;

    @JsonDeserialize(using = FlexibleBigDecimalDeserializer.class)
    private BigDecimal lastSalePrice;

    @JsonDeserialize(using = FlexibleBigDecimalDeserializer.class)
    private BigDecimal netChange;

    @JsonProperty("percentageChange")
    @JsonDeserialize(using = FlexibleBigDecimalDeserializer.class)
    private BigDecimal percentChange;



    public StockModel toDomain(){
        return StockModel.builder()
                .companyName(companyName)
                .symbol(symbol)
                .companyWebsite(companyWebsite)
                .marketCap(marketCap)
                .lastSalePrice(lastSalePrice)
                .netChange(netChange)
                .percentChange(percentChange)
                .build();
    }

    public StockPriceJpa toStockPriceJpa() {
        var stock = StockJpa.builder()
                .symbol(symbol)
                .build();

        return StockPriceJpa.builder()
                .marketCap(marketCap)
                .lastSalePrice(lastSalePrice)
                .netChange(netChange)
                .percentChange(percentChange)
                .symbol(symbol)
                .build();
    }

}
