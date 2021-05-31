package com.pp.index;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.pp.stocks.StockJpa;
import com.pp.stocks.StockModel;
import com.pp.stocks.StockRepository;
import com.pp.stocks.price.StockPriceRepository;
import com.pp.util.Utils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class IndexService {

    private IndexPriceCalculator indexPriceCalculator;
    private IndexRepository indexRepository;
    private StockRepository stockRepository;

    IndexService(NasdaqIndexPriceCalculator nasdaqIndexPriceCalculator,
                 IndexRepository indexRepository,
                 StockRepository stockRepository){
        this.indexPriceCalculator = nasdaqIndexPriceCalculator;
        this.indexRepository = indexRepository;
        this.stockRepository = stockRepository;
    }


    public Set<IndexModel> retrieveTrackedIndexes(){
        var trackedIndexes = indexRepository.findAll().stream()
                .map(IndexJpa::toDomain)
                .collect(Collectors.toSet());
        return trackedIndexes;
    }


    public IndexModel getIndexByName(String name) {
        var index = indexRepository.findByName(name);
        if (index == null){
            throw new IllegalArgumentException("We are not currently tracking this index price!");
        }
        var indexModel = index.toDomain();
        indexModel.setPrice(indexPriceCalculator.calculateIndexPrice(indexModel.getStocks()));
        return indexModel;
    }

    public Set<StockModel> getIndexStocks(String name) {
        Set<StockJpa> stockJpas = this.indexRepository.findByName(name).getStocks();
        var stockModelSet = stockJpas.stream().map(StockJpa::toDomain).collect(Collectors.toSet());

        return stockModelSet;
    }
}
