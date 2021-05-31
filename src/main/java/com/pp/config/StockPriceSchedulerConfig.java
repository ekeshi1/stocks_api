package com.pp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockPriceSchedulerConfig {

    @Value("")
    public  static  Integer NASDAQ_FIXED_RATE;
}
