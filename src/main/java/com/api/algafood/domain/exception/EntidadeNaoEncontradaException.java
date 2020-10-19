package com.api.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.NOT_FOUND/*, reason = "Entidade não encontrada"*/) //toda a vez que chamar essa exception, o retorno do status HTTP será notfound, e o reason serve para alterar message do erro será entidade não encontrada.
public class EntidadeNaoEncontradaException extends ResponseStatusException{

private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
		super(status, mensagem);
	}

	public EntidadeNaoEncontradaException(String mensagem) {
		this(HttpStatus.NOT_FOUND, mensagem);
	}
}
