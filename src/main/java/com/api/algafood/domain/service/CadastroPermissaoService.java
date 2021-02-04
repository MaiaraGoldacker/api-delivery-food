package com.api.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.api.algafood.domain.model.Permissao;
import com.api.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId).orElseThrow(
				() -> new PermissaoNaoEncontradaException(permissaoId));
	}
}
