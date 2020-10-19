package com.api.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND/*, reason = "Entidade não encontrada"*/) //toda a vez que chamar essa exception, o retorno do status HTTP será notfound, e o reason serve para alterar message do erro será entidade não encontrada.
public class EntidadeNaoEncontradaException extends RuntimeException{

private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
}
