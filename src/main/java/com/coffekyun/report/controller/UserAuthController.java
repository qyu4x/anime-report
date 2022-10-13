package com.coffekyun.report.controller;

import com.coffekyun.report.model.request.UserAuthRequest;
import com.coffekyun.report.model.response.UserAuthReponse;
import com.coffekyun.report.model.response.WebResponse;
import com.coffekyun.report.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anime")
@Slf4j
public class UserAuthController {

    private final UserAuthService userAuthService;

    @Autowired
    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping(
            value = "/user-auth",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<WebResponse<UserAuthReponse>> createUserAuth(@RequestBody UserAuthRequest userAuthRequest) {
        log.info("#call method create user auth");
        UserAuthReponse userAuthReponse = userAuthService.addUserAuth(userAuthRequest);
        return new ResponseEntity<>(
                new WebResponse<UserAuthReponse>(
                        200,
                        "OK",
                        userAuthReponse
                ), HttpStatus.OK
        );
    }
}
