package com.jjh.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Currency Exchange Service 프로젝트의 properties 파일에 spring.application.name과 똑같은 이름으로 맞춰주어야 한다.
@FeignClient(name="currency-exchange", url="http://localhost:8000")
public interface CurrencyExchangeProxy {

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    // 현재 여기서는 CurrencyExchange빈에 접근할 수 없다.
    public CurrencyConversion retrieveExchangeValue(
        @PathVariable String from, 
        @PathVariable String to);

   

}
