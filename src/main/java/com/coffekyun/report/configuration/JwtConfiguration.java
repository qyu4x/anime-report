package com.coffekyun.report.configuration;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;


@Configuration
@Getter
@Setter
@NoArgsConstructor
public class JwtConfiguration {

    @Value("${jwt.configuration.secretKey}")
    private String secretKey;

    @Value("${jwt.configuration.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.configuration.keyAuthority}")
    private String keyAuthority;

    @Value("${jwt.configuration.tokenExpirationAfterDay}")
    private Integer tokenExpirationAfterDay;


    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(this.secretKey.getBytes());
    }

    public String getAuthorizationHttpHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
