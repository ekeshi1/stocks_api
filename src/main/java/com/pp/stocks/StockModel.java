package com.pp.stocks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StockModel implements Serializable {

    private UUID id;

    private String symbol;

    private String companyName;

    private String companyWebsite;

    private String description;

    private BigDecimal marketCap;

    private BigDecimal lastSalePrice;

    private BigDecimal netChange;

    private BigDecimal percentChange;
}
