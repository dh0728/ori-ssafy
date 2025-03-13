package com.products.products.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Value("${server.port}")
    private String port;

    @GetMapping("/check")
    public String check() {
        return String.format("products port:%s", port);
    }
}
