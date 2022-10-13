package com.coffekyun.report.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtUsernameAndPasswordRequest {

    private String username;

    private String password;

}
