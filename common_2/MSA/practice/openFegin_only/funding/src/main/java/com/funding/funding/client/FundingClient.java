package com.funding.funding.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "funding-client", url = "http://localhost:8080/funding")
public interface FundingClient {


    @GetMapping("/check")
    String check();

}
