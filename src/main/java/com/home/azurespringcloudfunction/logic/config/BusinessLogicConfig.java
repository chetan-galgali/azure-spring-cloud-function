package com.home.azurespringcloudfunction.logic.config;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BusinessLogicConfig {
	@Bean
	public Function<String, String> uppercase() {
		return payload -> {
			String output = payload.toUpperCase();
			return String.format("Input: %s", output);
		};
	}

	@Bean
	public Function<String, String> reverse() {
		return payload -> new StringBuilder(payload).reverse().toString();
	}
}
