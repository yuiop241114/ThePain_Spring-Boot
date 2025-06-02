package com.kh.thepain.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
public class Configuration {
	
	@Bean
	public WebClient webClient() {
		return WebClient.create();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPassword() { return new BCryptPasswordEncoder(); };
	
}
