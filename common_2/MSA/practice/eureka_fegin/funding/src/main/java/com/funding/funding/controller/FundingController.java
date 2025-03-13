package com.funding.funding.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funding")
public class FundingController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/check")
    public String check() {
        return String.format("funding port:%s", port);
    }
}
