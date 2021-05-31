package com.pp.assetholding;

import com.pp.account.AccountJpa;
import com.pp.account.AssetBuyModel;
import com.pp.database.AbstractAuditingEntity;
import com.pp.stocks.StockJpa;
import com.pp.stocks.price.StockPriceJpa;
import com.pp.util.BigDecimalUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.UUID;

@Entity
@Table(name = "account_assets")
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Builder(toBuilder = true)
public class AssetHoldingJpa extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private BigDecimal amountInFiat;
    private BigDecimal avgBuyPrice;
    private BigDecimal nrOfShares;

    @Column(name="asset_symbol", updatable = false,nullable = false)
    private String assetSymbol;

    @Column(name = "account_id",nullable = false,updatable = false)
    private UUID accountID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinFormula("(" +
            "SELECT sp.id " +
            "FROM stock_price sp " +
            "WHERE sp.symbol = asset_symbol " +
            "ORDER BY sp.created_date DESC " +
            "LIMIT 1" +
            ")")
    private StockPriceJpa latestPrice;

    public AssetHoldingModel toDomain(){
        var netChange = BigDecimal.ZERO;
        var currentValueInFiat = BigDecimal.ZERO;
        var percentChange = BigDecimal.ZERO;
        if (latestPrice != null) {
            netChange = BigDecimalUtils.calculateNetChange(amountInFiat,nrOfShares,latestPrice.getLastSalePrice());
            currentValueInFiat = BigDecimalUtils.calculateCurrentFiatValue(latestPrice.getLastSalePrice(),nrOfShares);
            percentChange = BigDecimalUtils.calculatePercentChange(amountInFiat,nrOfShares,latestPrice.getLastSalePrice());
        }

        return AssetHoldingModel.builder()
                .id(id)
                .amountInFiat(amountInFiat)
                .avgBuyPrice(avgBuyPrice)
                .nrOfShares(nrOfShares)
                .assetSymbol(assetSymbol)
                .accountID(accountID)
                .netChange(netChange)
                .currentValueInFiat(currentValueInFiat)
                .percentChange(percentChange)
                .build();
    }

    public AssetHoldingJpa aggregateExistingHolding(BigDecimal extraAmountInFiat, BigDecimal latestPrice){
        var newShares = AssetHoldingModel.calculateNrOfShares(extraAmountInFiat,latestPrice);
        var totalShares = nrOfShares.add(newShares,MathContext.DECIMAL32);
        var newAmountInFiat = amountInFiat.add(extraAmountInFiat,MathContext.DECIMAL32);
        var newPrice =latestPrice.multiply(newShares.divide(totalShares,MathContext.DECIMAL32),MathContext.DECIMAL32)
                .add(avgBuyPrice.multiply(nrOfShares.divide(totalShares,MathContext.DECIMAL32),MathContext.DECIMAL32),MathContext.DECIMAL32);

        this.nrOfShares = totalShares;
        this.amountInFiat = newAmountInFiat;
        this.avgBuyPrice = newPrice;

        return this;
    }


    public static AssetHoldingJpa firstTimeHolding(BigDecimal amountInFiat, BigDecimal latestPrice, String assetSymbol,UUID accountID){

        return AssetHoldingJpa.builder()
                .amountInFiat(amountInFiat)
                .avgBuyPrice(latestPrice)
                .nrOfShares(AssetHoldingModel.calculateNrOfShares(amountInFiat,latestPrice))
                .assetSymbol(assetSymbol)
                .accountID(accountID)
                .build();
    }

}
