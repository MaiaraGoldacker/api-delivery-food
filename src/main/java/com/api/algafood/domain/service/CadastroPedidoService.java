package com.api.algafood.domain.service;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.algafood.domain.exception.PedidoNaoEncontradoException;
import com.api.algafood.domain.model.FormaPagamento;
import com.api.algafood.domain.model.ItemPedido;
import com.api.algafood.domain.model.Pedido;
import com.api.algafood.domain.model.Produto;
import com.api.algafood.domain.model.Restaurante;
import com.api.algafood.domain.model.StatusPedido;
import com.api.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	public Pedido buscarOuFalhar(Long grupoId) {
		return pedidoRepository.findById(grupoId).orElseThrow(
				() -> new PedidoNaoEncontradoException(grupoId));
	}
	
	@Transactional
	private Pedido salvar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
		
	private Pedido validarPedido(Pedido pedido) {
		cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
		
		for(ItemPedido itempedido : pedido.getItens()) {
			Produto produto = cadastroProdutoService.buscarOuFalhar(pedido.getRestaurante().getId(), itempedido.getProduto().getId());
			
		}

		return pedido;
	}
	
	private Pedido calcularPedido(Pedido pedido) {
		
		BigDecimal valorSubtotal = BigDecimal.ZERO;
		for(ItemPedido itempedido : pedido.getItens()) {
			
			Produto produto = cadastroProdutoService.buscarOuFalhar(pedido.getRestaurante().getId(), itempedido.getProduto().getId());
			itempedido.setPrecoUnitario(produto.getPreco());
			itempedido.setPrecoTotal(
					itempedido.getPrecoUnitario().multiply(BigDecimal.valueOf(itempedido.getQuantidade()))
					);
			
			
			valorSubtotal  = valorSubtotal.add(itempedido.getPrecoTotal());
		}
		
		pedido.setSubtotal(valorSubtotal);
		
		BigDecimal valorTotal = valorSubtotal;
		if	(pedido.getTaxaFrete() != null) {
			valorTotal = valorSubtotal.add(pedido.getTaxaFrete());
		} else {
			pedido.setTaxaFrete(BigDecimal.ZERO);
		}
		pedido.setValorTotal(valorTotal);
		
		return pedido;
	}
	
	@Transactional
	public Pedido emitirPedido(Pedido pedido) {
		validarPedido(pedido);
		pedido = calcularPedido(pedido);
		pedido.setStatus(StatusPedido.CRIADO);
		salvar(pedido);
		return pedido;
	}
}
