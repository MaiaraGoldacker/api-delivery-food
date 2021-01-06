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

import com.api.algafood.assembler.FormaPagamentoModelAssembler;
import com.api.algafood.assembler.FormaPagamentoModelDisassembler;
import com.api.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.api.algafood.domain.exception.NegocioException;
import com.api.algafood.domain.repository.FormaPagamentoRepository;
import com.api.algafood.domain.service.CadastroFormaPagamentoService;
import com.api.algafood.model.FormaPagamentoModel;
import com.api.algafood.model.input.FormaPagamentoInput;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoModelDisassembler formaPagamentoModelDisassembler;
	
	@GetMapping
	public List<FormaPagamentoModel> Listar(){
		return formaPagamentoModelAssembler.toCollectionModel(formaPagamentoRepository.findAll());
	}
	
	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoModel buscar(@PathVariable Long formaPagamentoId) {				
		return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		try {
			return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(
					formaPagamentoModelDisassembler.toDomainModel(formaPagamentoInput)));
		} catch(CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId){
		cadastroFormaPagamentoService.excluir(formaPagamentoId);
	}
	

	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
		var formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

		formaPagamentoModelDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
		return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamentoAtual));
	}
}
