package com.api.algafood.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.algafood.domain.model.Cozinha;
import com.api.algafood.model.input.CozinhaInput;

@Component
public class CozinhaModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainModel(CozinhaInput cozinhaInput) {
		return modelMapper.map(cozinhaInput, Cozinha.class);
	}
	
	public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
		modelMapper.map(cozinhaInput, cozinha);
	}

}
