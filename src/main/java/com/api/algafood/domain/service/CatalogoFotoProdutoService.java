package com.api.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.api.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.api.algafood.domain.model.FotoProduto;
import com.api.algafood.domain.repository.ProdutoRepository;
import com.api.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired 
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquvoExistente = null;
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if (fotoExistente.isPresent()) {
			nomeArquvoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}
		
		//primeiro salva, pois pode dar erro de banco ao salvar. Se salvar por ultimo, a foto vai estar no disco local, porém não vai estar salva em BD, e isso é um problema.
		foto.setNomeArquivo(nomeNovoArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush(); //Forçar a descarga de  qualquer coisa que estiver em fila
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(dadosArquivo)
				.build();
		
		fotoStorageService.substituir(nomeArquvoExistente, novaFoto);
				
		return foto;
	}
	
	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(()-> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
	}
	
}
