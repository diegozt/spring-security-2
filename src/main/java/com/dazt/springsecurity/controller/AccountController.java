package com.dazt.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/myAccount")
    private String getAccountDetails(String input) {
        return "Here are the account details retrieved from DB";
    }
}
