package com.pp.stocks;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import static com.pp.util.ControllerUtil.illegalArgumentWrapper;

@RestController
public class StockController {

    Logger logger = LogManager.getLogger(StockController.class);
    StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/api/stocks/{ticker}")
    public ResponseEntity<?> getStock(@PathVariable(name = "ticker") String symbol){
        return  illegalArgumentWrapper(() -> stockService.getStockBySymbol(symbol));
    }

}
