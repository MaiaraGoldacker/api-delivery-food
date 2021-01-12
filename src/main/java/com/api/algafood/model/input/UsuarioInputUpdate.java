package com.api.algafood.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioInputUpdate {

	@NotBlank
	private String nome;
	
	@NotBlank @Email
	private String email;
}
