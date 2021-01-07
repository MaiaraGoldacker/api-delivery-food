package com.api.algafood.model;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EnderecoModel {
		
	private String cep;	
	private String numero;
	private String logradouro;
	private String complemento;
	private String bairro;
	private CidadeResumoModel cidade;

}
