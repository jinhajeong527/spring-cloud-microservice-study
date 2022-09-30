package com.jjh.microservices.currencyexchangeservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;



@RestController
public class CircuitBreakerController {
    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
    @GetMapping("/sample-api")
    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse") // 첫번째 시도에 성공할 것이므로 재시도되지 않을 것이다. 실패 유발 코드를 추가할 것이다. 
    public String sampleApi() {
        logger.info("Sample Api call received");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return forEntity.getBody();
    }
    public String hardcodedResponse(Exception ex) {
        return "fallback-response";
    }
}