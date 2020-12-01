package com.api.algafood.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EstadoInput {
	
	@NotBlank	
	private String nome;

}
