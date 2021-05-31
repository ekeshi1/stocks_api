package com.pp.index;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;
import com.pp.stocks.StockModel;
import org.springframework.lang.NonNull;

import java.util.Set;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public  class IndexModel {

    private UUID id;
    private String name;
    private String websiteUrl;
    private String description;

    @Getter
    @JsonIgnore
    private Set<StockModel> stocks;

    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal price;

}
