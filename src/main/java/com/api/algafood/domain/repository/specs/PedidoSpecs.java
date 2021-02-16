package com.api.algafood.domain.repository.specs;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.api.algafood.domain.filter.PedidoFilter;
import com.api.algafood.domain.model.Pedido;

public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){
		return (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
			
			if(Pedido.class.equals(query.getResultType())) { //evitar que fetch seja adicionado indevidamento ao realizar a consulta de count no pageable, causando excessão
				//evitar vários selects
				root.fetch("restaurante");
				root.fetch("restaurante").fetch("cozinha");
				root.fetch("cliente");
			}
			
			//adicionar predicates no arraylist
			if (filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId())); //nome na classe pedido, e valor que quer filtrar
			}
			
			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId())); 
			}
			
			if (filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), 
						filtro.getDataCriacaoInicio())); 
			}
			
			if (filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), 
						filtro.getDataCriacaoFim())); 
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
		
	}
}
