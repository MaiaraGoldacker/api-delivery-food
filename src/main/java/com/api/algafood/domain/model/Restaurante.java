package com.api.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
	@ManyToOne
	@JoinColumn(name="idforma_pagamento", nullable=false)
	private FormaPagamento formaPagamento;
}