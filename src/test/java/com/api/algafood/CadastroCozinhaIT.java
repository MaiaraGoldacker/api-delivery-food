package com.api.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.api.algafood.domain.model.Cozinha;
import com.api.algafood.domain.repository.CozinhaRepository;
import com.api.algafood.util.DatabaseCleaner;
import com.api.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
//para levantar contexto do spring e deixar fazer uma requisição real
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {
	
	private static final int COZINHA_ID_INEXISTENTE = 100;

	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;

	@LocalServerPort
	private int port; 
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	//preparação para os testes funcionarem
	@BeforeEach
	public void setUp() {
		//para ajudar a ver os possíveis problemas no console
		//serve para ver a resposta completa
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath ="/cozinhas";
		
		databaseCleaner.clearTables();
		prepararDados();
		
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
	}
	
	@Test
	public void deveRetornarStatus200_quandoConsultarCozinhas() {	
		given()
			.accept(ContentType.JSON)
		.when()
				.get()
			.then()
			.statusCode(HttpStatus.OK.value()); //status ok 200
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
		given()
        	.accept(ContentType.JSON)
        .when()
        	.get()
        .then()
        .body("", hasSize(quantidadeCozinhasCadastradas));
	}
	
	@Test
	public void deveRetornar201_quandoCadastrarCozinha() {
		given().body(jsonCorretoCozinhaChinesa)
			   .contentType(ContentType.JSON)
			   .accept(ContentType.JSON)
		.when()
			   .post()
		.then()
		.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_quandoConsultarCozinhaExistente() {
		given()
		.pathParam("cozinhaId", cozinhaAmericana.getId())
		   .accept(ContentType.JSON)
		.when()
		   .get("/{cozinhaId}")
		.then().statusCode(HttpStatus.OK.value())
		.body("nome", equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void deveRetornarRespostaEStatus400_quandoConsultarCozinhaInexistente() {
		given()
		.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
		   .accept(ContentType.JSON)
		.when()
		   .get("/{cozinhaId}")
		.then().statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	//método que irá inserir registros responsáveis para validação dos testes
	private void prepararDados() {
	    Cozinha cozinhaTailandesa = new Cozinha();
	    cozinhaTailandesa.setNome("Tailandesa");
	    cozinhaRepository.save(cozinhaTailandesa);

	    cozinhaAmericana = new Cozinha();
	    cozinhaAmericana.setNome("Americana");
	    cozinhaRepository.save(cozinhaAmericana);
	    
	    quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}
}
