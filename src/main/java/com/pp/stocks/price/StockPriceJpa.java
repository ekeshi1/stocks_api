package com.pp.stocks.price;


import com.pp.database.AbstractAuditingEntity;
import com.pp.stocks.StockJpa;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "stock_price")
public class StockPriceJpa extends AbstractAuditingEntity implements Serializable {


    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(
            name="UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;


    private BigDecimal marketCap;

    private BigDecimal lastSalePrice;

    private BigDecimal netChange;

    private BigDecimal percentChange;

    private String symbol;

    @Override
    public String toString() {
        return "StockPriceJpa{" +
                "id=" + id +
                ", marketCap=" + marketCap +
                ", lastSalePrice=" + lastSalePrice +
                ", netChange=" + netChange +
                ", percentChange=" + percentChange +
                '}';
    }
}
