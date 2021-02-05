package com.api.algafood.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemPedidoModel {

	private Long id;
	private Integer quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;	
	private String observacao;
	private Long ProdutoId;
	private String ProdutoNome;
}
