package com.api.algafood.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.algafood.domain.model.Grupo;
import com.api.algafood.model.input.GrupoInput;

@Component
public class GrupoModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainModel(GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {	
		modelMapper.map(grupoInput, grupo);
	}
}
