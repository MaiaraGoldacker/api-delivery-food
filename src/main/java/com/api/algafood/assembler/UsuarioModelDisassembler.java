package com.api.algafood.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.algafood.domain.model.Usuario;
import com.api.algafood.model.input.UsuarioInput;
import com.api.algafood.model.input.UsuarioInputUpdate;

@Component
public class UsuarioModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainModel(UsuarioInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
				
		modelMapper.map(usuarioInput, usuario);
	}
	
	public void copyToDomainObject(UsuarioInputUpdate usuarioInputUpdate, Usuario usuario) {
		
		modelMapper.map(usuarioInputUpdate, usuario);
	}
}
