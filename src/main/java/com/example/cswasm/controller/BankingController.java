package com.example.cswasm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BankingController {
    @GetMapping("/")
    public String get() {
        return "index";
    }
}
