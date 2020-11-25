package com.api.algafood.core.modelmapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper ModelMapper() {
		return new ModelMapper();
	}

}
