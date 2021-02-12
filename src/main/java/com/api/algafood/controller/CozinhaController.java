package com.api.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.api.algafood.assembler.CozinhaModelAssembler;
import com.api.algafood.assembler.CozinhaModelDisassembler;
import com.api.algafood.domain.model.Cozinha;
import com.api.algafood.domain.repository.CozinhaRepository;
import com.api.algafood.domain.service.CadastroCozinhaService;
import com.api.algafood.model.CozinhaModel;
import com.api.algafood.model.input.CozinhaInput;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaModelDisassembler cozinhaModelDisassembler;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) //aceita apenas retornar get em formato json.
	public Page<CozinhaModel> Listar(@PageableDefault(size=2) Pageable pageable){ //default de quantos registro por pagina, por causa da anotaçõ @PageableDefault
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		List<CozinhaModel> cozinhas = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());
		Page<CozinhaModel>cozinhaModelPage = new PageImpl<>(cozinhas, pageable, cozinhasPage.getTotalElements());
		return   cozinhaModelPage; //getContenct extrai as cozinhas em listas dentro do Page
	}
	
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		return cozinhaModelAssembler.toModel(cadastroCozinhaService.buscarOuFalhar(cozinhaId));
	}
	
	@GetMapping("/nome/{nome}")
	public List<CozinhaModel> cozinhasPorNome(@PathVariable String nome){
		return cozinhaModelAssembler.toCollectionModel((cozinhaRepository.findByNomeContaining(nome)));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		return cozinhaModelAssembler.toModel(
				 cadastroCozinhaService.salvar( cozinhaModelDisassembler.toDomainModel(cozinhaInput)));
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput){
		var cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

		cozinhaModelDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		return cozinhaModelAssembler.toModel(cadastroCozinhaService.salvar(cozinhaAtual));
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
		cadastroCozinhaService.excluir(cozinhaId);
	}
}

