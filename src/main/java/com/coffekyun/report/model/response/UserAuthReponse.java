package com.coffekyun.report.model.response;

import com.coffekyun.report.model.enums.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserAuthReponse {

    private String username;

    private ApplicationUserRole userRole;

}
