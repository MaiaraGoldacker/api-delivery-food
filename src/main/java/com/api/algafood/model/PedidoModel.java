package com.api.algafood.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import com.api.algafood.domain.model.StatusPedido;
import com.api.algafood.model.input.UsuarioUpdateModel;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PedidoModel {
	
	private Long id;	
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataEntrega;
	private OffsetDateTime dataCancelamento;
	private EnderecoModel enderecoEntrega;
	private StatusPedido status;
	private List<ItemPedidoModel> itens = new ArrayList<>();
	private RestauranteResumoModel restaurante;
	private FormaPagamentoModel formaPagamento;
	private UsuarioUpdateModel cliente;
}
