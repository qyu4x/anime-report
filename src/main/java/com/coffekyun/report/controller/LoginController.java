package com.coffekyun.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping("/login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("/welcome")
    public String getSuccessView() {
        return "welcome";
    }

}
