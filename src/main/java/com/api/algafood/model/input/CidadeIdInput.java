package com.api.algafood.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CidadeIdInput {

	@NotNull
	private Long id;
}
