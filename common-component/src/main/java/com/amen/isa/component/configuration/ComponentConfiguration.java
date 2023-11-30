package com.amen.isa.component.configuration;

import com.amen.isa.component.client.ProductServiceClient;
import com.amen.isa.component.client.UserServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class ComponentConfiguration {
    @Bean
    @Qualifier("userServiceWebClient")
    public WebClient userServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .filter(logRequest())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return (request, next) -> {
            log.info("Request: {}", request);
            return next.exchange(request);
        };
    }

    @Bean
    public UserServiceClient userServiceClient(WebClient userServiceWebClient) {
        return new UserServiceClient(userServiceWebClient);
    }


//    @Bean
//    @Qualifier("productServiceWebClient")
//    public WebClient productServiceWebClient() {
//      FIXME: create reactive version of client
//    }

    @Bean
    @Qualifier("productServiceRestTemplate")
    public RestTemplate productServiceRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ProductServiceClient productServiceClient(RestTemplate productServiceRestTemplate) {
        return new ProductServiceClient(productServiceRestTemplate);
    }
}
