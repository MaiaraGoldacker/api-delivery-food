package com.api.algafood.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.api.algafood.domain.model.Estado;
import com.api.algafood.model.input.EstadoInput;

@Component
public class EstadoModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toDomainModel(EstadoInput estadoInput) {
		return modelMapper.map(estadoInput, Estado.class);
	}
	
	public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
		modelMapper.map(estadoInput, estado);
	}

}
