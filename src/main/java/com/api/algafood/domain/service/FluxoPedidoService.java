package com.api.algafood.domain.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(pedidoId);
		pedido.confirmar();		
	}


	@Transactional
	public void entregar(Long pedidoId) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(pedidoId);		
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(Long pedidoId) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(pedidoId);
		pedido.cancelar();
	}

}