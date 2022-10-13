package com.coffekyun.report.service.impl;

import com.coffekyun.report.entity.UserAuth;
import com.coffekyun.report.model.request.UserAuthRequest;
import com.coffekyun.report.model.response.UserAuthReponse;
import com.coffekyun.report.repository.UserAuthRepository;
import com.coffekyun.report.security.PasswordEncoderConfiguration;
import com.coffekyun.report.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAuthServiceImpl implements   UserAuthService {

    private final UserAuthRepository userAuthRepository;

    private final PasswordEncoderConfiguration passwordEncoderConfiguration;

    @Autowired
    public UserAuthServiceImpl(UserAuthRepository userAuthRepository, PasswordEncoderConfiguration passwordEncoderConfiguration) {
        this.userAuthRepository = userAuthRepository;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
    }

    @Override
    public UserAuthReponse addUserAuth(UserAuthRequest userAuthRequest) {
        log.info("#call add user auth service");
        UserAuth userAuth = new UserAuth(
                userAuthRequest.getUsername(),
                passwordEncoderConfiguration.passwordEncoder().encode(userAuthRequest.getPassword()),
                userAuthRequest.getUserRole()
        );

        userAuthRepository.save(userAuth);

        return new UserAuthReponse(
                userAuth.getUsername(),
                userAuth.getUserRole()
        );

    }
}
