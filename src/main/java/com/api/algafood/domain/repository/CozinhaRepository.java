package com.api.algafood.domain.repository;

import java.util.List;

import com.api.algafood.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();
	Cozinha salvar(Cozinha cozinha);
	Cozinha buscar(Long id);
	void remover(Long id);
}
