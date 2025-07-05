package com.kh.thepain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ThepainApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ThepainApplication.class, args);
	}

	/*
		jsp 사용을 위해 war + 외부 톰캣 사용을 위한 메소드
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ThepainApplication.class);
	}

}
