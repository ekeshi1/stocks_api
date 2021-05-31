package com.pp.stocks.price;

import com.pp.stocks.price.StockPriceJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockPriceRepository extends CrudRepository<StockPriceJpa, UUID> {

    Optional<StockPriceJpa> findFirstBySymbolOrderByCreatedDate(String symbol);
}
