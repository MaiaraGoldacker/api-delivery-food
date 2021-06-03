package com.api.algafood.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.algafood.assembler.FotoProdutoModelAssembler;
import com.api.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.api.algafood.domain.model.FotoProduto;
import com.api.algafood.domain.model.FotoProdutoModel;
import com.api.algafood.domain.model.Produto;
import com.api.algafood.domain.service.CadastroProdutoService;
import com.api.algafood.domain.service.CatalogoFotoProdutoService;
import com.api.algafood.domain.service.FotoStorageService;
import com.api.algafood.model.input.FotoProdutoInput;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();		
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());

		return fotoProdutoModelAssembler.toModel(fotoSalva);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		return fotoProdutoModelAssembler.toModel(fotoProduto);
	}
	
	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE) //Esse parâmetro é o tipo informado no Postman > Headers > Content-Type
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
		try {
		InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(new InputStreamResource(inputStream));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
}
