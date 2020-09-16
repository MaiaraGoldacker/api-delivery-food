package com.api.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.algafood.domain.exception.EntidadeEmUsoException;
import com.api.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.api.algafood.domain.model.Estado;
import com.api.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
		} catch (EmptyResultDataAccessException ex){	
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de estado com o código %d", estadoId));
			
		} catch(DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format("Estado de codigo %d não pode ser removida, pois está em uso", estadoId));
		}
	}
	
}
