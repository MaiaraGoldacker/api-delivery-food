package com.api.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.algafood.domain.exception.EntidadeEmUsoException;
import com.api.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.api.algafood.domain.model.Restaurante;
import com.api.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de codigo %d não pode ser removida, pois está em uso";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		var cozinha =  cadastroCozinhaService.buscarOuFalhar(cozinhaId); 
		
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException ex){	
			throw new RestauranteNaoEncontradoException(restauranteId);
		} catch(DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format(MSG_RESTAURANTE_EM_USO, restauranteId));			
		}
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(
				() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
}
