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

import com.api.algafood.assembler.GrupoModelAssembler;
import com.api.algafood.assembler.GrupoModelDisassembler;
import com.api.algafood.domain.exception.EstadoNaoEncontradoException;
import com.api.algafood.domain.exception.NegocioException;
import com.api.algafood.domain.repository.GrupoRepository;
import com.api.algafood.domain.service.CadastroGrupoService;
import com.api.algafood.model.GrupoModel;
import com.api.algafood.model.input.GrupoInput;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoModelDisassembler grupoModelDisassembler;
	
	@GetMapping
	public List<GrupoModel> Listar(){
		return  grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {
		return   grupoModelAssembler.toModel(cadastroGrupoService.buscarOuFalhar(grupoId));
	}
	
	@PutMapping("/{grupoId}")
	public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput){
		try {
			var grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);
			
			grupoModelDisassembler.copyToDomainObject(grupoInput, grupoAtual);
		
			return  grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupoAtual));
		} catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {		
		try {
			return 
					grupoModelAssembler.toModel(
					cadastroGrupoService.salvar( grupoModelDisassembler.toDomainModel(grupoInput)));
		} catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId){
		cadastroGrupoService.excluir(grupoId);	
	}
}
