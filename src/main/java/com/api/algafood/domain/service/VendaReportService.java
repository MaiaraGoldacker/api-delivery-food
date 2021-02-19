package com.api.algafood.domain.service;

import com.api.algafood.domain.filter.VendaDiariaFilter;

import net.sf.jasperreports.engine.JRException;

public interface VendaReportService {

	byte[] emitirVendasDiarias(VendaDiariaFilter vendaDiariaFilte,  String timeOffSet);
}
