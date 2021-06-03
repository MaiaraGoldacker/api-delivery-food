package com.api.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeArquivo);
	
	InputStream recuperar(String nomeArquivo); //
	
	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
		armazenar(novaFoto);
		
		if (nomeArquivoAntigo != null) {
			remover(nomeArquivoAntigo);
		}	
	}
	
	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + nomeOriginal; 
	}
	
	@Builder
	@Getter
	class NovaFoto{
		private String nomeArquivo;
		private InputStream inputStream; //Não usar multipartfile, pois esse tipo está ligado a protocolo http/web e nós já estamos em uma camada interna, mais de domínio.
	}
}
