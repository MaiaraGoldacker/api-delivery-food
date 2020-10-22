package com.api.algafood.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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

import com.api.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.api.algafood.domain.exception.NegocioException;
import com.api.algafood.domain.model.Restaurante;
import com.api.algafood.domain.repository.RestauranteRepository;
import com.api.algafood.domain.service.CadastroRestauranteService;


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
	public Restaurante buscar(@PathVariable Long restauranteId) {
		return cadastroRestauranteService.buscarOuFalhar(restauranteId);
	}
	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
		var restauranteAtual =  cadastroRestauranteService.buscarOuFalhar(restauranteId);
			
		BeanUtils.copyProperties(restaurante, restauranteAtual,
						"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
				
		try {
			return cadastroRestauranteService.salvar(restauranteAtual);
		} catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	/*@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId, 
			@RequestBody Map<String, Object> campos){
		var restauranteAtual = restauranteRepository.findById(restauranteId);
		
		if(restauranteAtual.isEmpty()) {
			return null;
			//return ResponseEntity.notFound().build();
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
	}*/
		
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		try {
			return cadastroRestauranteService.salvar(restaurante);
		} catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId){
		cadastroRestauranteService.excluir(restauranteId);
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
	
	@GetMapping("/consultarPorFreteGratis")
	public List<Restaurante> consultarPorFreteGratis(String nome){
		return restauranteRepository.findComFreteGratis(nome);
	}
	
	@GetMapping("/primeiro")
	public Optional<Restaurante> consultarPrimeiroRestaurante(){
		return restauranteRepository.buscarPrimeiro();
	}
}
