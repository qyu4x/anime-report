package com.coffekyun.report.jwt;

import com.coffekyun.report.configuration.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.elasticsearch.common.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtConfiguration jwtConfiguration;

    public JwtTokenVerifier(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(jwtConfiguration.getAuthorizationHttpHeader());

        if (Strings.isNullOrEmpty(authorization) || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.replace(jwtConfiguration.getTokenPrefix(), "");
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtConfiguration.getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();

            String subject = body.getSubject();
            List<Map<String, String>> auhtorities = (List<Map<String, String>>)  body.get("authorities");

            Set<SimpleGrantedAuthority> authority = auhtorities.stream()
                    .map(auth -> new SimpleGrantedAuthority(auth.get(jwtConfiguration.getKeyAuthority())))
                    .collect(Collectors.toSet());

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    subject,
                    null,
                    authority
            );

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }catch (JwtException exception) {
            throw new IllegalStateException(String.format("Opps, token %s cannot be trusted", token));
        }

        filterChain.doFilter(request, response);
    }
}
