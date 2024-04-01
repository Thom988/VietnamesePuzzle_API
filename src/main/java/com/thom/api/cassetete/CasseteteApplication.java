package com.thom.api.cassetete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thom.api.cassetete.service.CombinationService;

@SpringBootApplication
public class CasseteteApplication implements CommandLineRunner {
    
    	@Autowired
    	CombinationService combinationService;

	public static void main(String[] args) {
		SpringApplication.run(CasseteteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	    
	    combinationService.generateSolutions();
	}

}
