package com.api.algafood.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.algafood.domain.model.Estado;
import com.api.algafood.domain.model.Cidade;
import com.api.algafood.model.input.CidadeInput;

@Component
public class CidadeModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainModel(CidadeInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
		cidade.setEstado(new Estado()); //senão adicionar, ele tenta alterar o id do estado, ao invés de apenas modificar a estado linkada a Cidade
		
		modelMapper.map(cidadeInput, cidade);
	}
}
