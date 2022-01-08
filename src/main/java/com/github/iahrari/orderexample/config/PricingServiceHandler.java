package com.github.iahrari.orderexample.config;

import java.util.Optional;

import com.github.iahrari.orderexample.dto.AuthResponse;
import com.github.iahrari.orderexample.dto.AuthenticationUserDTO;
import com.github.iahrari.orderexample.dto.PriceModelDTO;
import com.github.iahrari.orderexample.exception.PriceResponseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PricingServiceHandler {
    public final static String PRICE_ENDPOINT = "/api/v1/price";
    private final static String AUTHENTICATION_ENDPOINT = "/api/v1/authentication";

    private final RestTemplate restTemplate;

    @Value("${pricing-service.username}")
    private String username;
    @Value("${pricing-service.password}")
    private String password;
    @Value("${pricing-service.url}")
    private String pricingServiceUrl;

    private volatile String token;

    synchronized private void authenticate(){
        try {
            var user = AuthenticationUserDTO.builder()
                            .username(username)
                            .password(password)
                            .build();
            
            var response = restTemplate.postForEntity(
                                pricingServiceUrl + AUTHENTICATION_ENDPOINT, 
                                user, AuthResponse.class
                            );

            token = response.getBody().getToken();
        } catch (HttpClientErrorException e) {
            throw new PriceResponseException(
                        String.format(
                            "Pricing service error code %d", 
                            e.getStatusCode().value()
                        ));
        }
    }

    public Double getPrice(String source, String destination) {
        var counter = 0;
        var price = PriceModelDTO.builder()
                .source(source)
                .destination(destination)
                .build();
        
        var headers = new HttpHeaders();
        var entity = new HttpEntity<>(price, headers);

        while(true){
            headers.setBearerAuth(token);
            try {
                var priceResponse = restTemplate.postForEntity(
                        pricingServiceUrl + PricingServiceHandler.PRICE_ENDPOINT, entity,
                        PriceModelDTO.class);

                return Optional.ofNullable(priceResponse.getBody())
                        .map(PriceModelDTO::getPrice)
                        .orElseThrow(() -> new PriceResponseException("Body is null"));
            } catch (HttpClientErrorException e) {
                if(e.getStatusCode().value() == 401 && counter++ == 0){
                    authenticate();
                } else 
                    throw new PriceResponseException(
                                String.format(
                                    "Pricing service error code %d", 
                                    e.getStatusCode().value()
                                ));
            }
        }
    }
}
