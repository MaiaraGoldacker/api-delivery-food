package com.api.algafood.validation;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor //cria construtor
@Getter
public class ValidacaoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private BindingResult bindingResult;

}
