package com.github.iahrari.orderexample.config.jwt;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Configuration
@RequiredArgsConstructor
public class JwtSecretKey {
    @Getter @Setter
    private String secretKey = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";
    

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
