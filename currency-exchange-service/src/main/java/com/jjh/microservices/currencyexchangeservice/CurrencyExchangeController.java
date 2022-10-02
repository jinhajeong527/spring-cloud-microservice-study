package com.jjh.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
    Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
    
    @Autowired
    CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    private Environment environment;

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
        @PathVariable String from, 
        @PathVariable String to) {
            logger.info("retrieveExchangevalue called with {} to {}", from, to);
            CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
            if(currencyExchange == null) {
                throw new RuntimeException("Unable to Find data for " +from + " to "+ to);
            }
            String port = environment.getProperty("local.server.port");
            currencyExchange.setEnvironment(port);
    
            return currencyExchange;
        }
    
    
}
