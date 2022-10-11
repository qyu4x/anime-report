package com.coffekyun.report.security;

import com.coffekyun.report.model.enums.ApplicationUserPermission;
import com.coffekyun.report.model.enums.ApplicationUserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/api/*", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/anime/pdf").hasRole(ApplicationUserRole.USER.name())
                .antMatchers(HttpMethod.POST, "/api/anime/add").hasAuthority(ApplicationUserPermission.ADMIN_WRITE.getDescription())
                .antMatchers(HttpMethod.GET, "/api/anime/all").hasAnyRole(ApplicationUserRole.USER.name(), ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMIN_TRAINEE.name())
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
                .authorities(ApplicationUserRole.USER.getGrantedAuthorities())
//                .roles(ApplicationUserRole.USER.name())
                .build();

        UserDetails userAdmin = User.builder()
                .username("hikaru")
                .password(passwordEncoderConfiguration.passwordEncoder().encode("kyun"))
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
//                .roles(ApplicationUserRole.ADMIN.name())
                .build();

        UserDetails userAdminTrainee = User.builder()
                .username("sagiri")
                .password(passwordEncoderConfiguration.passwordEncoder().encode("kyun"))
                .authorities(ApplicationUserRole.ADMIN_TRAINEE.getGrantedAuthorities())
//                .roles(ApplicationUserRole.ADMIN_TRAINEE.name())
                .build();

        return new InMemoryUserDetailsManager(
                userKanojo, userAdmin, userAdminTrainee
        );
    }
}
