package com.api.algafood.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.algafood.domain.exception.EntidadeEmUsoException;
import com.api.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.api.algafood.domain.model.Restaurante;
import com.api.algafood.domain.repository.RestauranteRepository;
import com.api.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@GetMapping
	public List<Restaurante> Listar(){
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		var restaurante = restauranteRepository.findById(restauranteId);
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
		try {
			var restauranteAtual = restauranteRepository.findById(restauranteId);
		
			if(restauranteAtual.isPresent()) {
				BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
				cadastroRestauranteService.salvar(restauranteAtual.get());
				return ResponseEntity.ok(restauranteAtual);
			}
			return ResponseEntity.notFound().build();
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, 
			@RequestBody Map<String, Object> campos){
		var restauranteAtual = restauranteRepository.findById(restauranteId);
		
		if(restauranteAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual.get());
		return atualizar(restauranteId, restauranteAtual.get());
	
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		
		camposOrigem.forEach((nomeProp, valorProp) ->{
			Field field = ReflectionUtils.findField(Restaurante.class, nomeProp);
			field.setAccessible(true); //muda tipo de acesso das propriedades- private para public
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem); //necessário para que tipo de lista seja convetido para o mesmo tipo que está na classe
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
		
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId){
		try {
			cadastroRestauranteService.excluir(restauranteId);
			return ResponseEntity.noContent().build();
		} catch(EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		} catch(EntidadeEmUsoException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@GetMapping("/taxa")
	public List<Restaurante> restaurantesPorTaxaFrete(
			BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
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
	public List<Restaurante> consultarPorNome(String nome, Long cozinhaId){
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}
	
	
	@GetMapping("/consultarPorNomeFrete")
	public List<Restaurante> consultarPorNomeFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
	}
}
