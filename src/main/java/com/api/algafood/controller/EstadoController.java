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

import com.api.algafood.assembler.EstadoModelAssembler;
import com.api.algafood.assembler.EstadoModelDisassembler;
import com.api.algafood.domain.repository.EstadoRepository;
import com.api.algafood.domain.service.CadastroEstadoService;
import com.api.algafood.model.EstadoModel;
import com.api.algafood.model.input.EstadoInput;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@Autowired
	private EstadoModelDisassembler estadoModelDisassembler;
	
	@GetMapping
	public List<EstadoModel> Listar(){
		return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		return  estadoModelAssembler.toModel(cadastroEstadoService.buscarOuFalhar(estadoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		return estadoModelAssembler.toModel(
				cadastroEstadoService.salvar(estadoModelDisassembler.toDomainModel(estadoInput)));
	}
	
	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput){	
		var estadoAtual = cadastroEstadoService.buscarOuFalhar(estadoId);
		
		estadoModelDisassembler.copyToDomainObject(estadoInput, estadoAtual);
		
		return estadoModelAssembler.toModel(cadastroEstadoService.salvar(estadoAtual));
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId){
		cadastroEstadoService.excluir(estadoId);
	}
}
