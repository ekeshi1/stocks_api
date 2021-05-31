package com.pp.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public  class BigDecimalUtils {
    private static BigDecimal BIG_DECIMAL_HUNDRED = BigDecimal.TEN.multiply(BigDecimal.TEN,MathContext.DECIMAL32);


    public static BigDecimal calculateNetChange(BigDecimal amountInFiat, BigDecimal nrOfShares, BigDecimal currentSharePrice){
        var currentFiatValue = calculateCurrentFiatValue(nrOfShares, currentSharePrice);

         return  currentFiatValue.subtract(amountInFiat,MathContext.DECIMAL32);
    }

    public static BigDecimal calculatePercentChange(BigDecimal amountInFiat, BigDecimal nrOfShares, BigDecimal currentSharePrice){
        var netChange = calculateNetChange(amountInFiat,nrOfShares,currentSharePrice);
        return netChange.divide(amountInFiat,MathContext.DECIMAL32).multiply(BIG_DECIMAL_HUNDRED);
    }

    public static BigDecimal calculatePercentChange(BigDecimal amountInFiat, BigDecimal currentAmountInFiat){
        var netChange = calculateNetChange(amountInFiat,currentAmountInFiat);
        return netChange.divide(amountInFiat,MathContext.DECIMAL32).multiply(BIG_DECIMAL_HUNDRED);
    }

    public static BigDecimal calculateNetChange(BigDecimal amountInFiat, BigDecimal currentAmountInFiat) {
        return  currentAmountInFiat.subtract(amountInFiat,MathContext.DECIMAL32);

    }

    public static BigDecimal calculateCurrentFiatValue(BigDecimal nrOfShares,BigDecimal currentSharePrice) {
        return nrOfShares.multiply(currentSharePrice,MathContext.DECIMAL32);
    }

}
