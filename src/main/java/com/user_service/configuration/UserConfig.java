package com.user_service.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class UserConfig {
	
	
	
	@Bean
	public ModelMapper modelMapper() {
	   return new ModelMapper();
	}
	

	

}
