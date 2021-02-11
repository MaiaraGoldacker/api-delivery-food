package com.api.algafood.domain.repository.specs;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.api.algafood.domain.model.Pedido;
import com.api.algafood.domain.repository.filter.PedidoFilter;

public class PedidoSpecs {

	public static Specification<Pedido> usandoFIltro(PedidoFilter filtro){
		return (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
					
			//evitar vários selects
			root.fetch("restaurante");
			root.fetch("restaurante").fetch("cozinha");
			root.fetch("cliente");
			
			
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
