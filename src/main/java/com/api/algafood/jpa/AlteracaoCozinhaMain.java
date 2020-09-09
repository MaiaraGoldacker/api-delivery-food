package com.api.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.api.algafood.AlgafoodApplication;
import com.api.algafood.domain.model.Cozinha;
import com.api.algafood.domain.repository.CozinhaRepository;

public class AlteracaoCozinhaMain {

	public static void main(String[] args) {
		//iniciando aplicação sem ser web
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		//chamando bean de cozinha para testes
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
			
		Cozinha cozinha1 = new Cozinha();	
		cozinha1.setId(1L);
		cozinha1.setNome("Brasileira");
	
		cozinhaRepository.salvar(cozinha1);
	
	}
}