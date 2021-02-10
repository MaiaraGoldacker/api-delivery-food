package com.api.algafood.model;

import java.math.BigDecimal;

import com.api.algafood.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class RestauranteModel {
	
	@JsonView(RestauranteView.ApenasNome.class)
	private Long id;
	
	@JsonView(RestauranteView.ApenasNome.class)
	private String nome;
	
	private BigDecimal taxaFrete;
	
	private CozinhaModel cozinha;
	
	private Boolean ativo;
	
	private Boolean aberto;
	
	private EnderecoModel endereco;
}
