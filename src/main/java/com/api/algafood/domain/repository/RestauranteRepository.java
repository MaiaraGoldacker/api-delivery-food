package com.api.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.api.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal); 
	//funciona com prefixos exclusivos, como findBy, query, get...
	
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);

	boolean existsByNome(String nome);
	
	int countByCozinhaId(Long cozinhaId);
}

