package com.jjh.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
        @PathVariable String from,
        @PathVariable String to,
        @PathVariable BigDecimal quantity) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = 
            new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
        CurrencyConversion.class, uriVariables); // 리스폰스 타입, uri 변수 키와 값 담은 해시맵 
        
        CurrencyConversion currencyConversion = responseEntity.getBody();
        return new CurrencyConversion(currencyConversion.getId(), from, to, quantity, 
                                      currencyConversion.getConversionMultiple(),
                                      quantity.multiply(currencyConversion.getConversionMultiple()),
                                      currencyConversion.getEnvironment());
    }
}
