package com.api.algafood.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.api.algafood.domain.model.Usuario;
import com.api.algafood.model.UsuarioModel;
import com.api.algafood.model.input.UsuarioUpdateModel;

@Component
public class UsuarioModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioModel toModel(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioModel.class);
	}
	
	public UsuarioUpdateModel toUpdateModel(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioUpdateModel.class);
	}
	
	public List<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios){
		return usuarios.stream()
				.map(usuario -> toModel(usuario))
				.collect(Collectors.toList());
	}
	
	public List<UsuarioUpdateModel> toCollectionUpdateModel(List<Usuario> usuarios){
		return usuarios.stream()
				.map(usuario -> toUpdateModel(usuario))
				.collect(Collectors.toList());
	}
	
}
