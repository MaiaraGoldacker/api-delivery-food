package com.api.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.api.algafood.validation.Groups;
import com.api.algafood.validation.TaxaFrete;
import com.api.algafood.validation.ValorZeroIncluiDescricao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

                          //se aqui for zero        verifica descrição       descrição que será procurada
@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "FreteGratis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//@NotNull  não pode ser null
	//@NotEmpty não pode ser vazio ou null
	@NotBlank //Não pode ser vazio, null, ou apenas espaços em branco
 	private String nome;
	
	@JsonIgnore
	@CreationTimestamp //adiciona data e hora atual quando propriedade for criada a 1ª vez
	@Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime") //columnDefinition= adiciona data sem precisão de milissegundos
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp //adiciona data e hora atual quando propriedade for atualizada
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDateTime dataAtualizacao;
	
	
	//@DecimalMin("1") //valor minimo deve ser zero.
	@NotNull
	//@PositiveOrZero //precisa ser um valor positivo ou zero
	@TaxaFrete
 	@Column(name = "taxa_frete",  nullable=false) //especifica coluna notnull no banco
	private BigDecimal taxaFrete;
	
	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
	joinColumns= @JoinColumn(name = "restaurante_id"), //coluna da relação manytomany da classe q está sendo criada o relacionamento
	inverseJoinColumns = @JoinColumn(name= "forma_pagamento_id")) //coluna da tabela inversa
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produto = new ArrayList<>();
}