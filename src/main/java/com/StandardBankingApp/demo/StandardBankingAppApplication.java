package com.StandardBankingApp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StandardBankingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StandardBankingAppApplication.class, args);
	}

}
