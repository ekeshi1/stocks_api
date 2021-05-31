package com.pp.index;

import com.pp.database.AbstractAuditingEntity;
import com.pp.stocks.StockJpa;
import com.pp.stocks.StockModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "index")
public class IndexJpa extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(
            name="UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String name;

    private String websiteUrl;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable( name = "index_composition",
    joinColumns = {
            @JoinColumn(name = "index_id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "stock_id")
    })
    private Set<StockJpa> stocks;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getDescription() {
        return description;
    }

    public Set<StockJpa> getStocks() {
        return stocks;
    }

    public IndexModel toDomain(){
        Set<StockModel> stockModelSet = new HashSet<>();

        return IndexModel.builder()
                .id(id)
                .description(description)
                .name(name)
                .websiteUrl(websiteUrl)
                .stocks(stockModelSet)
                .build();
    }
}
