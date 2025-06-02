package com.kh.thepain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ThepainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThepainApplication.class, args);
	}

}
