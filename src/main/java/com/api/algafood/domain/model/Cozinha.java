package com.api.algafood.domain.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;
//@JsonRootName("gastronomia") //muda o nome do root quando retornar em formato xml
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

	@EqualsAndHashCode.Include //Usar apenas o Id para geração de equels e hashcode
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//@JsonIgnore //remover propriedade da representação. Não aparecerá no retorno
	//@JsonProperty("titulo") //mudar o nome na representação do payload desse objeto 
	@Column(nullable = false)
	private String nome;

}
