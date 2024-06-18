package com.vladyslav.luckypets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiLuckyPetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiLuckyPetsApplication.class, args);
	}

}
