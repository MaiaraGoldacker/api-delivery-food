package com.api.algafood.domain.repository;

import java.util.List;

import com.api.algafood.domain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao> listar();
	Permissao salvar(Permissao permissao);
	Permissao buscar(Long id);
	void remover(Long id);
}
