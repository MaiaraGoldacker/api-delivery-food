package com.api.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.algafood.assembler.PedidoInputDisassembler;
import com.api.algafood.assembler.PedidoModelAssembler;
import com.api.algafood.assembler.PedidoResumoModelAssembler;
import com.api.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.api.algafood.domain.exception.NegocioException;
import com.api.algafood.domain.exception.PedidoNaoEncontradoException;
import com.api.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.api.algafood.domain.model.Pedido;
import com.api.algafood.domain.model.Usuario;
import com.api.algafood.domain.repository.PedidoRepository;
import com.api.algafood.domain.service.CadastroPedidoService;
import com.api.algafood.domain.service.CadastroUsuarioService;
import com.api.algafood.model.PedidoModel;
import com.api.algafood.model.PedidoResumoModel;
import com.api.algafood.model.input.PedidoInput;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	@GetMapping
	public List<PedidoResumoModel> Listar(){
		return  pedidoResumoModelAssembler.toCollectionModel(pedidoRepository.findAll());
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoModel buscar(@PathVariable Long pedidoId) {
		try {
			return pedidoModelAssembler.toModel(cadastroPedidoService.buscarOuFalhar(pedidoId));
		} catch (PedidoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(
			@RequestBody @Valid PedidoInput pedidoInput) {
		try {
			//setar cliente fixo, conforme instrução 
			Pedido pedido = pedidoInputDisassembler.toDomainModel(pedidoInput);
			
			Usuario usuario = cadastroUsuarioService.buscarOuFalhar(1L);
			pedido.setCliente(usuario);
			return pedidoModelAssembler.toModel(cadastroPedidoService.emitirPedido(pedido));

		} catch(PedidoNaoEncontradoException | RestauranteNaoEncontradoException | FormaPagamentoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
