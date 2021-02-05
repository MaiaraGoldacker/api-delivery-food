package com.api.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.algafood.domain.exception.PedidoNaoEncontradoException;
import com.api.algafood.domain.model.Pedido;
import com.api.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	public Pedido buscarOuFalhar(Long grupoId) {
		return pedidoRepository.findById(grupoId).orElseThrow(
				() -> new PedidoNaoEncontradoException(grupoId));
	}
	
}
