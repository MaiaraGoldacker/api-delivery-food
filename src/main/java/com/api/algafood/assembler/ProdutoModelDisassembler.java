package com.api.algafood.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.algafood.domain.model.Produto;
import com.api.algafood.model.input.ProdutoInput;

@Component
public class ProdutoModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toDomainModel(ProdutoInput produtoInput) {
		return modelMapper.map(produtoInput, Produto.class);
	}
	
	public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
				
		modelMapper.map(produtoInput, produto);
	}
	
}
