package com.api.algafood.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.algafood.assembler.RestauranteModelAssembler;
import com.api.algafood.assembler.RestauranteModelDisassembler;
import com.api.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.api.algafood.domain.exception.NegocioException;
import com.api.algafood.domain.repository.RestauranteRepository;
import com.api.algafood.domain.service.CadastroRestauranteService;
import com.api.algafood.model.RestauranteModel;
import com.api.algafood.model.input.RestauranteInput;


@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteModelDisassembler restauranteModelDisassembler;

	@GetMapping
	public List<RestauranteModel> Listar(){
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {		
		
		return restauranteModelAssembler.toModel(cadastroRestauranteService.buscarOuFalhar(restauranteId));
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput){
		
		try {
			var restauranteAtual =  cadastroRestauranteService.buscarOuFalhar(restauranteId);
			
			//BeanUtils.copyProperties(restaurante, restauranteAtual,
			//			"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
				
			restauranteModelDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch(CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(
			@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(
					restauranteModelDisassembler.toDomainModel(restauranteInput)));
		} catch(CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId){
		cadastroRestauranteService.excluir(restauranteId);
	}
	
	@GetMapping("/taxa")
	public List<RestauranteModel> restaurantesPorTaxaFrete(
			BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteModelAssembler.toCollectionModel(
				restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal));
	}
	
	@GetMapping("/existeNome")
	public boolean restaurantesExistsNome(String nome){
		return restauranteRepository.existsByNome(nome);
	}
	
	@GetMapping("/countCozinhas")
	public int restauranteCountCozinha(Long cozinhaId){
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
		
	@GetMapping("/consultarPorNome")
	public List<RestauranteModel> consultarPorNome(String nome, Long cozinhaId){
		return restauranteModelAssembler.toCollectionModel(
				restauranteRepository.consultarPorNome(nome, cozinhaId));
	}
	
	
	@GetMapping("/consultarPorNomeFrete")
	public List<RestauranteModel> consultarPorNomeFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteModelAssembler.toCollectionModel(
				restauranteRepository.find(nome, taxaInicial, taxaFinal));
	}
	
	@GetMapping("/consultarPorFreteGratis")
	public List<RestauranteModel> consultarPorFreteGratis(String nome){
		return restauranteModelAssembler.toCollectionModel(
				restauranteRepository.findComFreteGratis(nome));
	}
}
