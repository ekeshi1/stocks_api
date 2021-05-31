package com.pp.stocks;


import com.pp.stocks.price.StockPriceJpa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "stock")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StockJpa {

    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(
            name="UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String symbol;

    private String companyName;

    private String companyWebsite;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinFormula("(" +
            "SELECT sp.id " +
            "FROM stock_price sp " +
            "WHERE sp.symbol = symbol " +
            "ORDER BY sp.created_date DESC " +
            "LIMIT 1" +
            ")")
    private StockPriceJpa latestPrice;

    @Override
    public String toString() {
        return "StockJpa{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyWebsite='" + companyWebsite + '\'' +
                ", description='" + description + '\'' +
                ", latestPrice=" + latestPrice +
                '}';
    }


    public StockModel toDomain(){
        System.out.println("To Domain");
       return StockModel.builder()
                .id(id)
                .companyName(companyName)
                .symbol(symbol)
                .description(description)
                .companyWebsite(companyWebsite)
                .marketCap(latestPrice.getMarketCap())
                .percentChange(latestPrice.getPercentChange())
                .netChange(latestPrice.getNetChange())
                .lastSalePrice(latestPrice.getLastSalePrice())
                .build();

    }
}
