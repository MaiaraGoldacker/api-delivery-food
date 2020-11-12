package com.api.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.algafood.domain.exception.EntidadeEmUsoException;
import com.api.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.api.algafood.domain.model.Cozinha;
import com.api.algafood.domain.model.Restaurante;
import com.api.algafood.domain.service.CadastroCozinhaService;
import com.api.algafood.domain.service.CadastroRestauranteService;

@SpringBootTest
class CadastroCozinhaIntegrationTest {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
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

	@Test
	public void deveFalharQuandoExcluirCozinhaEmUso() {
		
		//cenário
		
		//cria uma cozinha
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("israelita");
		Cozinha cozinhaAdicionada = cadastroCozinhaService.salvar(novaCozinha);
		
		//cria um restaurante e vincula a cozinha adicionada
		Restaurante novoRestaurante = new Restaurante();
		novoRestaurante.setNome("Novo Restaurante");
		novoRestaurante.setTaxaFrete(BigDecimal.valueOf(2));
		novoRestaurante.setCozinha(cozinhaAdicionada);
		cadastroRestauranteService.salvar(novoRestaurante);
		
		//ação e validação 
		assertThrows(EntidadeEmUsoException.class, () -> cadastroCozinhaService.excluir(cozinhaAdicionada.getId()));
	}
	
	@Test
	public void deveFalharQuandoExcluirCozinhaInexistente() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> cadastroCozinhaService.excluir(9999L));
	}
	
}
