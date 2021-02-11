package com.api.algafood.core.config.squiggly;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {

	//toda requisição http que chegar no back vai passar por esse filtro do Squiggly
	//filtro de servlet
	@Bean
	public FilterRegistrationBean<SquigglyRequestFilter> SquigglyRequestFilter(ObjectMapper objectMapper){ //sping irá injetar objectMapper
		Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null)); //nome do parametro limitador será campos, e não fields
		
		var urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*"); //limitando filtro para urls especificas
		
		var filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();
		filterRegistration.setFilter(new SquigglyRequestFilter());
		filterRegistration.setOrder(1);
		filterRegistration.setUrlPatterns(urlPatterns);
		return filterRegistration;
	}
}
