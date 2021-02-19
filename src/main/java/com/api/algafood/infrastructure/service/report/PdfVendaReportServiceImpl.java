package com.api.algafood.infrastructure.service.report;


import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.algafood.domain.filter.VendaDiariaFilter;
import com.api.algafood.domain.service.VendaQueryService;
import com.api.algafood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportServiceImpl implements VendaReportService{

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffSet) {
		try {
			var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");
		
			var parametros = new HashMap<String, Object>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
			var vendasDiarias = vendaQueryService.conultarVendasDiarias(vendaDiariaFilter, timeOffSet);
			var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
		
			//objeto que representa um relatorio preenchido - não é PDF
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);
		
			// TODO Auto-generated method stub
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {			
			throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
		}
	}
}
