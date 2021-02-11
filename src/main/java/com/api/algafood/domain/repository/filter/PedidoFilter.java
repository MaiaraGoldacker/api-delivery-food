package com.api.algafood.domain.repository.filter;
import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoFilter {

	//propriedades opcionais, com qualquer combinação de filtro E/OU
	private Long clienteId;
	private Long restauranteId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME) //forçando a formatação da data-hora para esse formato
	private OffsetDateTime dataCriacaoInicio;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;
}
