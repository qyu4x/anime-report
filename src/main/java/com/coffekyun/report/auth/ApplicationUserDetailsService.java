package com.coffekyun.report.auth;

import com.coffekyun.report.entity.UserAuth;
import com.coffekyun.report.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public ApplicationUserDetailsService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRepository.findByUsername(username)
                .stream().findFirst().orElseThrow(() -> {
                    throw new UsernameNotFoundException(String.format("username %s is not found", username));
                });

        return new ApplicationUserDetailsAuth(
                userAuth.getUserRole().getGrantedAuthorities(),
                userAuth.getPassword(),
                userAuth.getUsername(),
                true,
                true,
                true,
                userAuth.isEnabled()
        );
    }
}
