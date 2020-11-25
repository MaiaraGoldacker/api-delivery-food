package com.api.algafood.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.algafood.domain.model.Restaurante;
import com.api.algafood.model.input.RestauranteInput;

@Component
public class RestauranteModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainModel(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
}
