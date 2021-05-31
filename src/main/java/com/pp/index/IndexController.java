package com.pp.index;


import com.pp.stocks.StockController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.pp.util.ControllerUtil.illegalArgumentWrapper;

@RestController
public class IndexController {

    private IndexService indexService;


    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("/api/indexes")
    public ResponseEntity<?> getTrackedIndexes(){
        return  illegalArgumentWrapper(() -> indexService.retrieveTrackedIndexes());
    }

    @GetMapping("/api/indexes/{indexName}")
    public ResponseEntity<?> getSingleIndex(@PathVariable(name = "indexName") String name){
        return  illegalArgumentWrapper(() -> indexService.getIndexByName(name));
    }

    @GetMapping("/api/indexes/{indexName}/stocks")
    public ResponseEntity<?> getIndexStocks(@PathVariable(name = "indexName") String name){
        return  illegalArgumentWrapper(() -> indexService.getIndexStocks(name));
    }
}
