package com.pp.account;


import com.pp.assetholding.AssetHoldingJpa;
import com.pp.assetholding.AssetHoldingModel;
import com.pp.database.AbstractAuditingEntity;
import com.pp.stocks.StockJpa;
import com.pp.user.UserJpa;
import com.pp.util.BigDecimalUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AccountJpa extends AbstractAuditingEntity {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String name;

    private BigDecimal fiatBalance;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private List<AssetHoldingJpa> holdings;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserJpa user;

    public AccountModel toDomain() {
        //calculate account net change
        List<AssetHoldingModel> assetHoldingModels = new ArrayList<>();

        if(holdings != null){
            assetHoldingModels = holdings.stream()
                    .map(AssetHoldingJpa::toDomain)
                    .collect(Collectors.toList());
        }
        var account =  AccountModel.builder()
                .fiatBalance(fiatBalance)
                .id(id)
                .name(name)
                .holdings(assetHoldingModels)
                .currentValue(BigDecimal.ZERO)
                .build();

        BigDecimal totalAmountInFiat = BigDecimal.ZERO;
        BigDecimal currentAmountInFiat = BigDecimal.ZERO;

        if (account.getHoldings() != null && account.getHoldings().size() > 0) {
            for(AssetHoldingModel assetHoldingModel : account.getHoldings()){
                totalAmountInFiat = totalAmountInFiat.add(assetHoldingModel.getAmountInFiat(),MathContext.DECIMAL32);
                currentAmountInFiat = currentAmountInFiat.add(assetHoldingModel.getCurrentValueInFiat(),MathContext.DECIMAL32);
            };

            account.setCurrentValue(currentAmountInFiat);
            account.setNetChange(BigDecimalUtils.calculateNetChange(totalAmountInFiat, currentAmountInFiat));
            account.setPercentChange(BigDecimalUtils.calculatePercentChange(totalAmountInFiat, currentAmountInFiat));
        }

        return  account;
    }


    public void topupBalance(BigDecimal balanceToAdd) {
        if (balanceToAdd.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Only topUp with positive amount is eligible.!");
        }
        this.fiatBalance = this.fiatBalance.add(balanceToAdd, MathContext.DECIMAL32);
    }

    public void debitBalance(BigDecimal debitAmount) {
        if (debitAmount.compareTo(fiatBalance) > 0) {
            throw new IllegalArgumentException("Not enough funds");
        }
        this.fiatBalance = this.fiatBalance.subtract(debitAmount, MathContext.DECIMAL32);
    }

}
