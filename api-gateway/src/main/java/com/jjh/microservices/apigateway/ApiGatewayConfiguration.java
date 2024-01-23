package com.jjh.microservices.apigateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
    @Bean // we can customize route which we want to use
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
//        Function<PredicateSpec, Buildable<Route>> routeFunction
//                = p -> p.path("/get")
//                        .uri("http://httpbin.org:80");
        return builder.routes()
        // 여기서 커스터마이징을 한다.
        // Function을 사용해서 커스터마이징을 할 수 있다.
        .route(p -> p.path("/get") // host, header 등을 통해서도 매칭할 수 있다.
                    .filters(f ->
                    // this can be authentication header
                            f.addRequestHeader("MyHeader", "MyURI")
                            .addRequestParameter("Param", "MyValue"))
                    .uri("http://httpbin.org:80"))
        .route(p -> p.path("/currency-exchange/**")
        // Naming server를 사용해 리다이렉트 한다. + 로드 밸런싱
                    .uri("lb://currency-exchange") // the name of the registration on the eureka server
               )
        .route(p -> p.path("/currency-conversion/**")
                    .uri("lb://currency-conversion")
               )
        .route(p -> p.path("/currency-conversion-feign/**")
                    .uri("lb://currency-conversion")
               )
        .route(p -> p.path("/currency-conversion-new/**")
                    .filters(f -> f.rewritePath(
                        "/currency-conversion-new/(?<segment>.*)",
                        "/currency-conversion-feign/${segment}"))
                    .uri("lb://currency-conversion")
               )
        .build();
    }

}
