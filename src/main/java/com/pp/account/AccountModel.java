package com.pp.account;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pp.assetholding.AssetHoldingModel;
import com.pp.stocks.StockJpa;
import com.pp.user.UserJpa;
import com.pp.user.details.AccountCreationModel;
import com.pp.util.MoneySerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public class AccountModel implements Serializable {
    private UUID id ;
    private String name;

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal fiatBalance;
    private List<AssetHoldingModel> holdings;

    @Setter
    @JsonSerialize(using = MoneySerializer.class)
    @JsonProperty("currentAssetsValue")
    private BigDecimal currentValue = BigDecimal.ZERO;

    @Setter
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal netChange = BigDecimal.ZERO;

    @Setter
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal percentChange = BigDecimal.ZERO;

    public static AccountJpa newJpa(AccountCreationModel model, UserJpa user) {
        return AccountJpa.builder()
                .name(model.getName())
                .user(user)
                .fiatBalance(BigDecimal.ZERO)
                .build();
    }
}
