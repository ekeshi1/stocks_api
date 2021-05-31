package com.pp.index;

import com.pp.stocks.StockModel;

import java.math.BigDecimal;
import java.util.Set;

public interface IndexPriceCalculator {

    BigDecimal calculateIndexPrice(Set<StockModel> stockModels);

}
