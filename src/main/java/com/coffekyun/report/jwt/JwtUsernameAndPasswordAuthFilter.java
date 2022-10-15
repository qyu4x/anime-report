package com.coffekyun.report.jwt;

import com.coffekyun.report.configuration.JwtConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtConfiguration jwtConfiguration;
    public JwtUsernameAndPasswordAuthFilter(AuthenticationManager authenticationManager, JwtConfiguration jwtConfiguration) {
        this.authenticationManager = authenticationManager;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            JwtUsernameAndPasswordRequest jwtUsernameAndPasswordRequest =
                    new ObjectMapper().readValue(request.getInputStream(), JwtUsernameAndPasswordRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
              jwtUsernameAndPasswordRequest.getUsername(),
              jwtUsernameAndPasswordRequest.getPassword()
            );

            return authenticationManager.authenticate(authentication);
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim(jwtConfiguration.getKeyAuthority(), authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getTokenExpirationAfterDay())))
                .signWith(jwtConfiguration.getSecretKey())
                .compact();

        response.addHeader(jwtConfiguration.getAuthorizationHttpHeader(), jwtConfiguration.getTokenPrefix() + token);
    }
}
