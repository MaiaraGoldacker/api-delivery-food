package com.api.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class VendaDiariaFilter {

	private Long restauranteId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME) //forçando a formatação da data-hora para esse formato
	private OffsetDateTime dataCriacaoInicio;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;
}
