package com.dazt.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @GetMapping("/myBalances")
    public String getBalance(String input) {
        return "Here are the balance details from DB";
    }
}
