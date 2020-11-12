package com.api.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.algafood.domain.model.Cozinha;
import com.api.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest
class CadastroCozinhaIntegrationTest {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Test
	public void DeveCadastrarCozinhaComSucesso() {
		//cenário
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Chinesa");
		
		//ação
		Cozinha cozinhaNova = cadastroCozinhaService.salvar(cozinha);
		
		//validação
		assertThat(cozinhaNova).isNotNull();
		assertThat(cozinhaNova.getId()).isNotNull();
	}
	
	
	@Test
	public void deveFalharAoCadastrarCozinha_QuandoSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		assertThrows(ConstraintViolationException.class, () ->  cadastroCozinhaService.salvar(novaCozinha));
	}

}
