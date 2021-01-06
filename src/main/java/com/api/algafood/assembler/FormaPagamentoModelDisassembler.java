package com.api.algafood.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.algafood.domain.model.FormaPagamento;
import com.api.algafood.model.input.FormaPagamentoInput;

@Component
public class FormaPagamentoModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainModel(FormaPagamentoInput formaPagamentoInput) {
		return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoInput, formaPagamento);
	}
}
