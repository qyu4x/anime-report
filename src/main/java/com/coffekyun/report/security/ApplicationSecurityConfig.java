package com.coffekyun.report.security;

import com.coffekyun.report.auth.ApplicationUserDetailsService;
import com.coffekyun.report.configuration.JwtConfiguration;
import com.coffekyun.report.jwt.JwtTokenVerifier;
import com.coffekyun.report.jwt.JwtUsernameAndPasswordAuthFilter;
import com.coffekyun.report.model.enums.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

    private final PasswordEncoderConfiguration passwordEncoderConfiguration;

    private final ApplicationUserDetailsService applicationUserDetailsService;

    private final JwtConfiguration jwtConfiguration;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoderConfiguration passwordEncoderConfiguration,
                                     ApplicationUserDetailsService applicationUserDetailsService,
                                     JwtConfiguration jwtConfiguration) {
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
        this.applicationUserDetailsService = applicationUserDetailsService;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthFilter(authenticationConfiguration.getAuthenticationManager(), jwtConfiguration))
                .addFilterAfter(new JwtTokenVerifier(jwtConfiguration), JwtUsernameAndPasswordAuthFilter.class)
                .authorizeRequests()
                .antMatchers("/", "/api/*", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/anime/pdf").hasRole(ApplicationUserRole.USER.name())
//                .antMatchers(HttpMethod.POST, "/api/anime/add").hasAuthority(ApplicationUserPermission.ADMIN_WRITE.getDescription())
//                .antMatchers(HttpMethod.GET, "/api/anime/all").hasAnyRole(ApplicationUserRole.USER.name(), ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMIN_TRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic()
//                .formLogin()
//                    .loginPage("/login").permitAll()
//                    .defaultSuccessUrl("/welcome", true)
//                    .usernameParameter("username") // if you want to do customization, for form param request
//                    .passwordParameter("password")
//                .and()
//                .rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)) // default is 2 weeks, but this settig is 30 day
//                    .key("x-secure-nya")
//                    .rememberMeParameter("remember-me")
//                .and()
//                .logout()
//                    .logoutUrl("/logout")
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // use this, if csrf is disable, to secure our api, remove this line , if yours csrf is enable(default), because method is post not get
//                    .clearAuthentication(true)
//                    .deleteCookies("remember-me", "JSESSIONID").logoutSuccessUrl("/login")
//                .and()
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        return authenticationManagerBuilder
                .authenticationProvider(daoAuthenticationProvider()).build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoderConfiguration.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(applicationUserDetailsService);
        return daoAuthenticationProvider;
    }

//    @Bean
//    public UserDetailsService userConfiguration() {
//        UserDetails userKanojo = User.builder()
//                .username("kaguya")
//                .password(passwordEncoderConfiguration.passwordEncoder().encode("kanojo"))
//                .authorities(ApplicationUserRole.USER.getGrantedAuthorities())
////                .roles(ApplicationUserRole.USER.name())
//                .build();
//
//        UserDetails userAdmin = User.builder()
//                .username("hikaru")
//                .password(passwordEncoderConfiguration.passwordEncoder().encode("kyun"))
//                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
////                .roles(ApplicationUserRole.ADMIN.name())
//                .build();
//
//        UserDetails userAdminTrainee = User.builder()
//                .username("sagiri")
//                .password(passwordEncoderConfiguration.passwordEncoder().encode("kyun"))
//                .authorities(ApplicationUserRole.ADMIN_TRAINEE.getGrantedAuthorities())
////                .roles(ApplicationUserRole.ADMIN_TRAINEE.name())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                userKanojo, userAdmin, userAdminTrainee
//        );
//    }
}
