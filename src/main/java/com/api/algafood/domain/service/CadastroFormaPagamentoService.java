package com.api.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.algafood.domain.exception.EntidadeEmUsoException;
import com.api.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.api.algafood.domain.model.FormaPagamento;
import com.api.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	private static final String MSG_FORMAPAGAMENTO_EM_USO = "Forma Pagamento de codigo %d não pode ser removida, pois está em uso";
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	@Transactional
	public void excluir(Long formaPagamentoId) {
		try {
			formaPagamentoRepository.deleteById(formaPagamentoId);
			formaPagamentoRepository.flush();
		} catch (EmptyResultDataAccessException ex){	
			throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
			
		} catch(DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format(MSG_FORMAPAGAMENTO_EM_USO, formaPagamentoId));
		}
	}
	
	public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId).orElseThrow(
				() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
	}
}
