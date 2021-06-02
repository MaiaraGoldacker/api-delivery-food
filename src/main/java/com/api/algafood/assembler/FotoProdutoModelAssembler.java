package com.api.algafood.assembler;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.api.algafood.domain.model.FotoProduto;
import com.api.algafood.domain.model.FotoProdutoModel;

@Component
public class FotoProdutoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoModel toModel(FotoProduto fotoProduto) {
		return modelMapper.map(fotoProduto, FotoProdutoModel.class);
	}

}
