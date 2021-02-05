package com.api.algafood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.algafood.assembler.PedidoModelAssembler;
import com.api.algafood.assembler.PedidoResumoModelAssembler;
import com.api.algafood.domain.exception.NegocioException;
import com.api.algafood.domain.exception.PedidoNaoEncontradoException;
import com.api.algafood.domain.repository.PedidoRepository;
import com.api.algafood.domain.service.CadastroPedidoService;
import com.api.algafood.model.PedidoModel;
import com.api.algafood.model.PedidoResumoModel;

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
}
