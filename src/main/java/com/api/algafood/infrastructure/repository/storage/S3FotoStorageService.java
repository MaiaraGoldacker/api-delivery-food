package com.api.algafood.infrastructure.repository.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.api.algafood.domain.service.FotoStorageService;

//@Service
public class S3FotoStorageService implements FotoStorageService{

	@Autowired
	private AmazonS3 amazonS3;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		
	}

	@Override
	public void remover(String nomeArquivo) {
	
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
		return null;
	}

}
