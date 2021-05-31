package com.pp.assetholding;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pp.stocks.StockJpa;
import com.pp.stocks.StockModel;
import com.pp.util.MoneySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class AssetHoldingModel implements Serializable {
    private UUID id;
    private String assetSymbol;

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amountInFiat;

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal avgBuyPrice;
    private BigDecimal nrOfShares;
    private UUID accountID;

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal currentValueInFiat;

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal netChange;

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal percentChange;



    static BigDecimal calculateNrOfShares(BigDecimal amount, BigDecimal latestPrice){
        return amount.divide(latestPrice, MathContext.DECIMAL32);
    }

}
