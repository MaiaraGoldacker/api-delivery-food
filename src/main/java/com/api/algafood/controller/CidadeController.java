package com.api.algafood.controller;

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

import com.api.algafood.assembler.CidadeModelAssembler;
import com.api.algafood.assembler.CidadeModelDisassembler;
import com.api.algafood.domain.exception.EstadoNaoEncontradoException;
import com.api.algafood.domain.exception.NegocioException;
import com.api.algafood.domain.repository.CidadeRepository;
import com.api.algafood.domain.service.CadastroCidadeService;
import com.api.algafood.model.CidadeModel;
import com.api.algafood.model.input.CidadeInput;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeModelDisassembler cidadeModelDisassembler;
	
	@GetMapping
	public List<CidadeModel> Listar(){
		return  cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}
	
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		return   cidadeModelAssembler.toModel(cadastroCidadeService.buscarOuFalhar(cidadeId));
	}
	
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput){
		try {
			var cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);
			
			cidadeModelDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			//BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			
			return  cidadeModelAssembler.toModel(cadastroCidadeService.salvar(cidadeAtual));
		} catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {		
		try {
			return 
					cidadeModelAssembler.toModel(
					cadastroCidadeService.salvar( cidadeModelDisassembler.toDomainModel(cidadeInput)));
		} catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId){
		cadastroCidadeService.excluir(cidadeId);	
	}
	
}
