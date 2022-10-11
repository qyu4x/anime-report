package com.coffekyun.report.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig  {

    private PasswordEncoderConfiguration passwordEncoderConfiguration;

    public ApplicationSecurityConfig(PasswordEncoderConfiguration passwordEncoderConfiguration) {
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers("/", "/api/*", "index", "/css/*", "/js/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userConfiguration() {
        UserDetails userKanojo = User.builder()
                .username("kaguya")
                .password(passwordEncoderConfiguration.passwordEncoder().encode("kanojo"))
                .roles("KANOJO")
                .build();

        UserDetails userAdmin= User.builder()
                .username("hikaru")
                .password(passwordEncoderConfiguration.passwordEncoder().encode("kyun"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(
                userKanojo, userAdmin
        );
    }
}
