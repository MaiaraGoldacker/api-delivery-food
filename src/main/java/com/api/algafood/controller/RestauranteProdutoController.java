package com.api.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.api.algafood.assembler.ProdutoModelAssembler;
import com.api.algafood.assembler.ProdutoModelDisassembler;
import com.api.algafood.domain.exception.NegocioException;
import com.api.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.api.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.api.algafood.domain.model.Produto;
import com.api.algafood.domain.model.Restaurante;
import com.api.algafood.domain.service.CadastroProdutoService;
import com.api.algafood.domain.service.CadastroRestauranteService;
import com.api.algafood.model.ProdutoModel;
import com.api.algafood.model.input.ProdutoInput;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private ProdutoModelDisassembler produtoModelDisassembler;
	
	@GetMapping
	public List<ProdutoModel> listar(@PathVariable Long restauranteId){
		Restaurante restaurante  = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		return produtoModelAssembler.toCollectionModel(restaurante.getProdutos());
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
		return produtoModelAssembler.toModel(cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(
			@RequestBody @Valid ProdutoInput produtoInput,
			@PathVariable Long restauranteId) {
		try {
			Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

			Produto produto = produtoModelDisassembler.toDomainModel(produtoInput);			
			produto.setRestaurante(restaurante);
			
			return produtoModelAssembler.toModel(cadastroProdutoService.salvar(produto));
		} catch(ProdutoNaoEncontradoException |  RestauranteNaoEncontradoException  e ) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoModel alterar(
			@RequestBody @Valid ProdutoInput produtoInput,
			@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		try {
			Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);			
			produtoModelDisassembler.copyToDomainObject(produtoInput, produto);			
			produto = cadastroProdutoService.salvar(produto);		      
		    return produtoModelAssembler.toModel(produto);
		    
		} catch(ProdutoNaoEncontradoException | RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
