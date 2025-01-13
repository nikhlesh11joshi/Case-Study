package com.test.iris.card_validation_service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CardValidationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardValidationServiceApplication.class, args);
		System.out.println("Card-Validation-Service is running and Up");
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
