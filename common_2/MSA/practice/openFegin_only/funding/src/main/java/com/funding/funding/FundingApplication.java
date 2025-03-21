package com.funding.funding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FundingApplication {

	public static void main(String[] args) {

		SpringApplication.run(FundingApplication.class, args);
	}

}
