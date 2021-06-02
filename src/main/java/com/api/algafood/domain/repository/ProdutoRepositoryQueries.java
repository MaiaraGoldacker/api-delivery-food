package com.api.algafood.domain.repository;

import com.api.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto foto);
	void delete(FotoProduto foto);
}
