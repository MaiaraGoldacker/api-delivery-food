package com.api.algafood.infrastructure.repository.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.api.algafood.domain.exception.StorageException;
import com.api.algafood.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService{

	@Value("${algafood.storage.local.diretorio-fotos}")
	private Path diretorioFotos;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
		
		try {
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (IOException e) {			
			throw new StorageException("Não foi possível armazenar o arquivo", e);
		}	
	}
	
	private Path getArquivoPath(String nomeArquivo) {
		//concatenar Path = resolve
		 return diretorioFotos.resolve(Path.of(nomeArquivo));
	}

}
