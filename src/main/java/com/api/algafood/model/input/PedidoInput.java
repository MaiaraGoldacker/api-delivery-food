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
	
	/*
	 * 
	 * POST pedidos/
obrigatorios
restaurante{id}
formapagto{id}
enderecoEntrega{completo, + cidade{id}}
itensPedido {produtoId, quantidade, observacao} SizeMin1

cliente fixo de id 1
CALCULAR VALORES no pedido

validação
Forma de pagto '' não é aceita por esse restaurante
Nao existe um cadastro de produto com codigo 1 para restaurante de codigo 2

	 * 
	 */

}
