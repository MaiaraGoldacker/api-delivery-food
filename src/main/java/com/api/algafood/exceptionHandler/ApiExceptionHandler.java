package com.api.algafood.exceptionHandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.algafood.domain.exception.EntidadeEmUsoException;
import com.api.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.api.algafood.domain.exception.NegocioException;

@ControllerAdvice //Diz que as exceptions de todo  o projeto serão tratadas por essa anotação
public class ApiExceptionHandler {
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e){
		Problema problema = Problema.builder()
									.dataHora(LocalDateTime.now())
									.mensagem(e.getMessage())
									.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException e){
		Problema problema = Problema.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException e){
		Problema problema = Problema.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(problema);
	}
	
	//Não suporta formato XML - é possível customizar tratamentos de exceptions internas
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> tratarHttpMediaTypeNotSupportedException(){
		Problema problema = Problema.builder()
				.dataHora(LocalDateTime.now())
				.mensagem("O tipo de mídia não é aeito")
				.build();
		
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problema);
	}

}
