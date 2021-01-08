package com.api.algafood.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.api.algafood.model.input.CidadeIdInput;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EnderecoInput {

	@NotBlank
	private String cep;
	
	@NotBlank
	private String numero;
	
	@NotBlank
	private String logradouro;
	
	private String complemento;
	
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeIdInput cidade;
}
