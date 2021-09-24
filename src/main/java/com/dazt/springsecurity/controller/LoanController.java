package com.dazt.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    @GetMapping("myLoans")
    public String getLoanDetail(String input) {
        return "These are my loan details from DB";
    }
}
