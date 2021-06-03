package com.api.algafood.domain.service;

import java.io.InputStream;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	void armazenar(NovaFoto novaFoto);
	
	@Builder
	@Getter
	class NovaFoto{
		private String nomeArquivo;
		private InputStream inputStream; //Não usar multipartfile, pois esse tipo está ligado a protocolo http/web e nós já estamos em uma camada interna, mais de domínio.
	}
}
