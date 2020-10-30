package com.api.algafood.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "entidade não encontrada");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.title = title;
		this.uri = "http://algafood.com.br" + path;
	}

}
