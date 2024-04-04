package com.thom.api.cassetete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.thom.api.cassetete.service.CombinationService;

@SpringBootApplication
public class CasseteteApplication implements WebMvcConfigurer {
    
    	@Autowired
    	CombinationService combinationService;

	public static void main(String[] args) {
		SpringApplication.run(CasseteteApplication.class, args);
	}
	
	 @Override
	 public void addCorsMappings(CorsRegistry registry) {
	     registry.addMapping("/**")
	             .allowedOrigins("http://localhost:4200")
	             .allowedMethods("GET", "POST", "PUT", "DELETE")
	             .allowedHeaders("*");
	 }

}
