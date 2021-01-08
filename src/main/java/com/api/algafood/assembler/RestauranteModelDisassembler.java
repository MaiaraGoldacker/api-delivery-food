package com.api.algafood.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.algafood.domain.model.Cidade;
import com.api.algafood.domain.model.Cozinha;
import com.api.algafood.domain.model.Restaurante;
import com.api.algafood.model.input.RestauranteInput;

@Component
public class RestauranteModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainModel(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}

	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		restaurante.setCozinha(new Cozinha()); // senão adicionar, ele tenta alterar o id da cozinha, ao invés de apenas
												// modificar a cozinha linkada ao Restaurante

		if (restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}

		modelMapper.map(restauranteInput, restaurante);
	}
}
