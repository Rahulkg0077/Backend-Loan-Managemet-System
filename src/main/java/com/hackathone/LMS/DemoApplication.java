package com.hackathone.LMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableScheduling
@SpringBootApplication
public class DemoApplication {

	 @Bean
	 public CorsFilter corsFilter() {
	 final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	 final CorsConfiguration config = new CorsConfiguration();
	 config.setAllowCredentials(false);
	 config.addAllowedOrigin("*");
	 config.addAllowedHeader("*");
	 config.addAllowedMethod("OPTIONS");
	 config.addAllowedMethod("HEAD");
	 config.addAllowedMethod("GET");
	 config.addAllowedMethod("PUT");
	 config.addAllowedMethod("POST");
	 config.addAllowedMethod("DELETE");
	 config.addAllowedMethod("PATCH");
	 source.registerCorsConfiguration("/**", config);
	 return new CorsFilter(source);
	 }
	 
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
