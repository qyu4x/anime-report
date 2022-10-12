package com.coffekyun.report.security;

import com.coffekyun.report.model.enums.ApplicationUserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig  {

    private PasswordEncoderConfiguration passwordEncoderConfiguration;

    public ApplicationSecurityConfig(PasswordEncoderConfiguration passwordEncoderConfiguration) {
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/api/*", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/anime/pdf").hasRole(ApplicationUserRole.USER.name())
//                .antMatchers(HttpMethod.POST, "/api/anime/add").hasAuthority(ApplicationUserPermission.ADMIN_WRITE.getDescription())
//                .antMatchers(HttpMethod.GET, "/api/anime/all").hasAnyRole(ApplicationUserRole.USER.name(), ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMIN_TRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/welcome", true)
                    .usernameParameter("username") // if you want to do customization, for form param request
                    .passwordParameter("password")
                .and()
                .rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)) // default is 2 weeks, but this settig is 30 day
                    .key("x-secure-nya")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // use this, if csrf is disable, to secure our api, remove this line , if yours csrf is enable(default), because method is post not get
                    .clearAuthentication(true)
                    .deleteCookies("remember-me", "JSESSIONID").logoutSuccessUrl("/login")
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
