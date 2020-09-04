package com.api.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.api.algafood.AlgafoodApplication;
import com.api.algafood.domain.model.Cozinha;

public class InclusaoCozinhaMain {

	public static void main(String[] args) {
		//iniciando aplicação sem ser web
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		//chamando bean de cozinha para testes
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
			
		Cozinha cozinha1 = new Cozinha();
		Cozinha cozinha2 = new Cozinha();
		
		cozinha1.setNome("Brasileira");
		cozinha2.setNome("Japonesa");
		
		cadastroCozinha.salvar(cozinha1);
		cadastroCozinha.salvar(cozinha2);
	}
}
