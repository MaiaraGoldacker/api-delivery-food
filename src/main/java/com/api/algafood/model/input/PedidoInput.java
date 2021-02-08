package com.api.algafood.model.input;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.api.algafood.model.EnderecoInput;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PedidoInput {

	@Valid
	@NotNull
	private RestaurantePedidoInput restaurante;

	@Valid
	@NotNull
	private FormaPagamentoPedidoInput formaPagamento;

	@Valid
	@NotNull
	private EnderecoInput enderecoEntrega;
	
	@Valid
	@Size(min = 1)
	@NotNull
	private List<ItemPedidoInput> itens = new ArrayList<>();
}
