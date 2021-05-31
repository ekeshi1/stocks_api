package com.pp;

import com.pp.config.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Clock;

@SpringBootApplication
@EnableScheduling
public class StocksBackendApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksBackendApiApplication.class, args);
    }
    @Bean
    @Primary
    Clock clock() {
        return Clock.system(Constants.ZONE_ID);
    }

    @Bean
    public WebClient webClient(){
        return   WebClient.create();
    }
}
