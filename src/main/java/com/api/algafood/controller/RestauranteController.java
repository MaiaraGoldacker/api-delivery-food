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
import com.api.algafood.domain.exception.CidadeNaoEncontradaException;
import com.api.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.api.algafood.domain.exception.NegocioException;
import com.api.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.api.algafood.domain.repository.RestauranteRepository;
import com.api.algafood.domain.service.CadastroRestauranteService;
import com.api.algafood.model.RestauranteModel;
import com.api.algafood.model.input.RestauranteInput;
import com.api.algafood.model.view.RestauranteView;
import com.api.algafood.model.view.RestauranteView.ApenasNome;
import com.fasterxml.jackson.annotation.JsonView;


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
	public List<RestauranteModel> listar(){
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
						
			restauranteModelDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
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
		} catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@JsonView(ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteModel> listarApenasNomes(){
		return listar();
	}
	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId){
		cadastroRestauranteService.excluir(restauranteId);
	}
	
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restaurantesId) {
		try {
			cadastroRestauranteService.ativar(restaurantesId);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restaurantesId) {
		try {
			cadastroRestauranteService.inativar(restaurantesId);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e); //necessário para retornar 400 - erro correto, que é um status de erro do cliente, 404 é não encontrado 
		}
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
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.inativar(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		cadastroRestauranteService.abrir(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.fechar(restauranteId);
	}
}
