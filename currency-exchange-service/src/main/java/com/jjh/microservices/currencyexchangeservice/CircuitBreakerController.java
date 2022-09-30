package com.jjh.microservices.currencyexchangeservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;



@RestController
public class CircuitBreakerController {
    
    @GetMapping("/sample-api")
    //@Retry // 첫번째 시도에 성공할 것이므로 재시도되지 않을 것이다. 실패 유발 코드를 추가할 것이다. 
    public String sampleApi() {
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return forEntity.getBody();
    }
}