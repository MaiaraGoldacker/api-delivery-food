package com.api.algafood.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FormaPagamentoPedidoInput {

	@NotNull
	private Long id;
}
