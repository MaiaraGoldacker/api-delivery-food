package com.api.algafood.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RestaurantePedidoInput {

	@NotNull
	private Long id;
}
