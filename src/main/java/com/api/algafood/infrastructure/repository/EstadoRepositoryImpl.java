package com.api.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.algafood.domain.model.Estado;
import com.api.algafood.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Estado> listar(){
		TypedQuery<Estado> query =  manager.createQuery("from Estado", Estado.class);	
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public Estado salvar(Estado estado) {
		return manager.merge(estado);
	}
	
	@Override
	public Estado buscar(Long id) {
		return manager.find(Estado.class, id);
	}
	
	@Override
	@Transactional
	public void remover(Long id) {
		Estado estado = buscar(id);
		manager.remove(estado);
	}
}
