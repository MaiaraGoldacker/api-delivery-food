package com.api.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.algafood.domain.exception.EntidadeEmUsoException;
import com.api.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.api.algafood.domain.model.Restaurante;
import com.api.algafood.domain.repository.CozinhaRepository;
import com.api.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de codigo %d não pode ser removida, pois está em uso";
	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe um cadastro de restaurante com o código %d";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		var cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(()->new EntidadeNaoEncontradaException(
				String.format("Não existe cadastro de cozinha com o código %d", cozinhaId))); 
		
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}
	
	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
		} catch (EmptyResultDataAccessException ex){	
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId));
		} catch(DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(
						String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
	}
	
}
