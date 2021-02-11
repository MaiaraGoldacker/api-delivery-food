package com.api.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.api.algafood.domain.repository.filter.PedidoFilter;
import com.api.algafood.domain.repository.specs.PedidoSpecs;
import com.api.algafood.domain.service.CadastroPedidoService;
import com.api.algafood.domain.service.CadastroUsuarioService;
import com.api.algafood.model.PedidoModel;
import com.api.algafood.model.PedidoResumoModel;
import com.api.algafood.model.input.PedidoInput;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

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
	public List<PedidoResumoModel> pesquisar(PedidoFilter filtro){
		return  pedidoResumoModelAssembler.toCollectionModel(pedidoRepository.findAll(PedidoSpecs.usandoFIltro(filtro)));
	}
	
	/*@GetMapping
	public MappingJacksonValue listar(@RequestParam(required = false) String campos){
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoResumoModel> pedidosModel  = pedidoResumoModelAssembler.toCollectionModel(pedidos);
		
		//envelopando lista de pedido dentro de MappingJacksonValue, pois este permite customizar a serealização na resposta
		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
		
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());//serializa todos os campos
		
		if(StringUtils.isNotBlank(campos)) {
			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(","))); //quebra a string separando por virgulas e colocando em um array
		}
				
		pedidosWrapper.setFilters(filterProvider);
		
		return pedidosWrapper;  
	}*/
	
	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		try {
			return pedidoModelAssembler.toModel(cadastroPedidoService.buscarOuFalhar(codigoPedido));
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
