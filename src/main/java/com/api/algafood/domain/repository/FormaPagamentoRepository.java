package com.api.algafood.domain.repository;

import java.util.List;

import com.api.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {

	List<FormaPagamento> listar();
	FormaPagamento salvar(FormaPagamento formaPagamento);
	FormaPagamento buscar(Long id);
	void remover(Long id);
}
