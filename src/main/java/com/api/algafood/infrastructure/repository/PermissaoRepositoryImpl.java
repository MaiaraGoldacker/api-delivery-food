package com.api.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.algafood.domain.model.Permissao;
import com.api.algafood.domain.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Permissao> listar(){
		TypedQuery<Permissao> query =  manager.createQuery("from Permissao", Permissao.class);	
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public Permissao salvar(Permissao permissao) {
		return manager.merge(permissao);
	}
	
	@Override
	public Permissao buscar(Long id) {
		return manager.find(Permissao.class, id);
	}
	
	@Override
	@Transactional
	public void remover(Long id) {
		Permissao permissao = buscar(id);
		manager.remove(permissao);
	}
}
