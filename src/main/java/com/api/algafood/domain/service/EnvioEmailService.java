package com.api.algafood.domain.service;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem);
	
	@Getter
	@Builder
	class Mensagem{
		@Singular
		private Set<String> destinatarios; //tira o S do 'destinatarios' quando chamada essa propriedade. 
		
		@NonNull  //o nonNull do lombok fará com que se a propriedade estiver nula ao ser instânciada, gerara exception
		private String assunto;
		
		@NonNull
		private String corpo;
	}
}
