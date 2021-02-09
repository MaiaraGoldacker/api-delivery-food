package com.api.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{

	
private static final long serialVersionUID = 1L;
	
	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format( "Não existe um pedido com o código %s", codigoPedido));
	}
}
