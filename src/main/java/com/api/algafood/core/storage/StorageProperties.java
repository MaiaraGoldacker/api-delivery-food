package com.api.algafood.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.storage") //bind com application.properties
public class StorageProperties {
	
	private Local local = new Local();
	private S3 s3 = new S3();
	private TipoStorage tipo = TipoStorage.LOCAL;
	
	public enum TipoStorage {
		LOCAL, S3
	}
	
	@Getter
	@Setter
	public class Local{
		private Path diretorioFotos; //faz o bind automaticamente, pois segue o mesmo caminho - algafood.storage.local.diretorio-fotos - 
								     //precisa pensar nos nomes ao fazer esse bind, pois precisa ser o msm do caminho e propriedades
	}
	
	@Getter
	@Setter
	public class S3{
		private String idChaveAcesso;
		private String chaveAcessoSecreta;
		private String bucket;
		private String regiao;
		private String diretorioFotos;
	}
}
