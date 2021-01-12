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

import com.api.algafood.assembler.UsuarioModelAssembler;
import com.api.algafood.assembler.UsuarioModelDisassembler;
import com.api.algafood.domain.exception.EstadoNaoEncontradoException;
import com.api.algafood.domain.exception.NegocioException;
import com.api.algafood.domain.repository.UsuarioRepository;
import com.api.algafood.domain.service.CadastroUsuarioService;
import com.api.algafood.model.UsuarioModel;
import com.api.algafood.model.input.UsuarioInput;
import com.api.algafood.model.input.UsuarioInputUpdate;
import com.api.algafood.model.input.UsuarioUpdateModel;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioModelDisassembler usuarioModelDisassembler;
	
	@GetMapping
	public List<UsuarioUpdateModel> Listar(){
		return  usuarioModelAssembler.toCollectionUpdateModel(usuarioRepository.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		return   usuarioModelAssembler.toModel(cadastroUsuarioService.buscarOuFalhar(usuarioId));
	}
	
	@PutMapping("/{usuarioId}")
	public UsuarioUpdateModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputUpdate usuarioInput){
		try {
			var usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);
			
			usuarioModelDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
		
			return  usuarioModelAssembler.toUpdateModel(cadastroUsuarioService.salvar(usuarioAtual));
		} catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioUpdateModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {		
		try {
			return 
					usuarioModelAssembler.toUpdateModel(
					cadastroUsuarioService.salvar( usuarioModelDisassembler.toDomainModel(usuarioInput)));
		} catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long usuarioId){
		cadastroUsuarioService.excluir(usuarioId);	
	}
	
}
