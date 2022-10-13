package com.coffekyun.report.model.request;

import com.coffekyun.report.model.enums.ApplicationUserRole;
import lombok.Getter;

@Getter
public class UserAuthRequest {

    private String username;

    private String password;

    private ApplicationUserRole userRole;

}
