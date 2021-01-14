package com.api.algafood.model.input;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ProdutoInput {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;
	
	@NotNull
	private BigDecimal preco;
	
	@NotNull
	private Boolean ativo;
}
