package com.api.algafood.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.algafood.assembler.FotoProdutoModelAssembler;
import com.api.algafood.domain.model.FotoProduto;
import com.api.algafood.domain.model.FotoProdutoModel;
import com.api.algafood.domain.model.Produto;
import com.api.algafood.domain.service.CadastroProdutoService;
import com.api.algafood.domain.service.CatalogoFotoProdutoService;
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
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {

		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();		
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto);

		return fotoProdutoModelAssembler.toModel(fotoSalva);
	}
	
}
