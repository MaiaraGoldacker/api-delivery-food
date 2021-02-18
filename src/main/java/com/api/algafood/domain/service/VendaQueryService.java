package com.api.algafood.domain.service;

import java.util.List;

import com.api.algafood.domain.filter.VendaDiariaFilter;
import com.api.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {
	
	List<VendaDiaria> conultarVendasDiarias(VendaDiariaFilter vendaDiariaFilte,  String timeOffSet);
}
