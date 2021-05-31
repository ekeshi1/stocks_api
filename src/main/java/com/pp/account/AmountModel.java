package com.pp.account;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class AmountModel {
    @NotNull(message = "Amount can't be null")
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal amount;

    //TODO: Support currency
}
