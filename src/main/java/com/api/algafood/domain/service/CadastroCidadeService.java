package com.api.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.algafood.domain.exception.EntidadeEmUsoException;
import com.api.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.api.algafood.domain.model.Cidade;
import com.api.algafood.domain.repository.EstadoRepository;
import com.api.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "Cidade de codigo %d não pode ser removida, pois está em uso";
	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com o código %d";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		var estado = estadoRepository.findById(estadoId).orElseThrow(()->new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de estado com o código %d", estadoId))); 

		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
		} catch (EmptyResultDataAccessException ex){	
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));
		} catch(DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}
	
	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(
						String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
	}
}
