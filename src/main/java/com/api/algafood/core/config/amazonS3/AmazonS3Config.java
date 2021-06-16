package com.api.algafood.core.config.amazonS3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.api.algafood.core.storage.StorageProperties;
import com.api.algafood.core.storage.StorageProperties.TipoStorage;
import com.api.algafood.domain.service.FotoStorageService;
import com.api.algafood.infrastructure.repository.storage.LocalFotoStorageService;
import com.api.algafood.infrastructure.repository.storage.S3FotoStorageService;

@Configuration
public class AmazonS3Config {

	@Autowired
	private StorageProperties storageProperties;
	
	@Bean
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(), 
				storageProperties.getS3().getChaveAcessoSecreta());  //Precisa passar chaves de acesso
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao())
				.build(); //retorna uma instância de amazon S3
	}
	
	//método que define se será utilizado disco local ou amazonS3
	public FotoStorageService fotoStorageService() {
		
		if (TipoStorage.S3.equals(storageProperties.getTipo())) {
			return new S3FotoStorageService();
		} else {
			return new LocalFotoStorageService();
		}
	}
}
