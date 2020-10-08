package com.api.algafood.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Column(nullable=false) //espeficica coluna notnull no banco
	private BigDecimal taxaFrete;
	
	@ManyToOne
	@JoinColumn(name="idcozinha", nullable=false)
	private Cozinha cozinha;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
	joinColumns= @JoinColumn(name = "restaurante_id"), //coluna da relação manytomany da classe q está sendo criada o relacionamento
	inverseJoinColumns = @JoinColumn(name= "forma_pagamento_id")) //coluna da tabela inversa
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
}