package com.api.algafood.infrastructure.repository.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.api.algafood.core.storage.StorageProperties;
import com.api.algafood.domain.exception.StorageException;
import com.api.algafood.domain.service.FotoStorageService;

@Service
public class S3FotoStorageService implements FotoStorageService{

	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
		
			var objectMetadata = new com.amazonaws.services.s3.model.ObjectMetadata();
		objectMetadata.setContentType(novaFoto.getContentType()); //Para que o content type seja compatível com o tipo da imagem, sendo assim possível visualizá-lo na amazon sem fazer download
			
			var putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket(), 
					caminhoArquivo, 
					novaFoto.getInputStream(), 
					objectMetadata
					).withCannedAcl(CannedAccessControlList.PublicRead) //Adicionando a permissão de leitura publica, para que seja possível visualizar a imagem na amazon
					; //submeter para a API da amazon, uma requisição para adicionar um objeto
		
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
		}
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
			var deleteObjectRequest = new  DeleteObjectRequest(storageProperties.getS3().getBucket(), caminhoArquivo);
			amazonS3.deleteObject(deleteObjectRequest);
		} catch(Exception e) {
			throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
		}
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {		
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
		
		return FotoRecuperada.builder().url(url.toString()).build();
	}

}
