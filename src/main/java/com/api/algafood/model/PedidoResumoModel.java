package com.api.algafood.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import com.api.algafood.domain.model.StatusPedido;
import com.api.algafood.model.input.UsuarioUpdateModel;

import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter") substituido pelo squiggly
@Setter @Getter
public class PedidoResumoModel {
	
	private String codigo;	
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;	
	private StatusPedido status;
	private RestauranteResumoModel restaurante;
	private UsuarioUpdateModel cliente;
}
