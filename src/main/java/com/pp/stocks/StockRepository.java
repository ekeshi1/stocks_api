package com.pp.stocks;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepository extends CrudRepository<StockJpa, UUID> {


     Optional<StockJpa> findBySymbol(String symbol);

}
