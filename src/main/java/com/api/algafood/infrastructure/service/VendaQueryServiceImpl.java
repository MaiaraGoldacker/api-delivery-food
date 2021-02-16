package com.api.algafood.infrastructure.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.api.algafood.domain.filter.VendaDiariaFilter;
import com.api.algafood.domain.model.Pedido;
import com.api.algafood.domain.model.dto.VendaDiaria;
import com.api.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<VendaDiaria> conultarVendasDiarias(VendaDiariaFilter filtro) {

		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class); // VendaDiaria.class = tipo de retorno esperado na consulta
		var root = query.from(Pedido.class);

		var functionDateDataCriacao = builder.function("date", Date.class, root.get("dataCriacao")); // usar uma função do banco de dados(nome da funcao, tipo esperado do retorno, campo da função)

		var selection = builder.construct(VendaDiaria.class, functionDateDataCriacao, builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));

		/*
		 * select date(p.data_criacao) as data_criacao, count(p.id) as total_vendas,
		 * sum(p.valor_total) as total_faturado
		 * 
		 * from pedido group by date(p.data_criacao)
		 */
		//root.get(status) adicionar predicate fixo (where p.status IN)-> status = CONFIRMADO, ENTREGUE
		
		query.select(selection);
		//query.where() array.predicates
		query.groupBy(functionDateDataCriacao);
		return manager.createQuery(query).getResultList();
	}

}
