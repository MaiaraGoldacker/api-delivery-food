package com.api.algafood.core.modelmapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.algafood.domain.model.Endereco;
import com.api.algafood.model.EnderecoModel;

import org.modelmapper.ModelMapper;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper ModelMapper() {
		var modelMapper = new ModelMapper();
		var EnderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		
		EnderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(), 
				(dest, value) -> dest.getCidade().setEstado(value));
				
		
		return modelMapper;
	}

}
