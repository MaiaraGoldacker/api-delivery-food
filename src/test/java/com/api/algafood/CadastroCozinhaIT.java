package com.api.algafood;

import static io.restassured.RestAssured.given;
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

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
//para levantar contexto do spring e deixar fazer uma requisição real
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

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
	public void deveConter2Cozinhas_quandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
				.get()
			.then()
			.body("", hasSize(2));
			//.body("nome", Matchers.hasItems("Indiana", "Tailandesa"));
	}
	
	@Test
	public void deveRetornar201_quandoCadastrarCozinha() {
		given().body("{\"nome\" : \"Chinesa\"}")
			   .contentType(ContentType.JSON)
			   .accept(ContentType.JSON)
		.when()
			   .post()
		.then()
		.statusCode(HttpStatus.CREATED.value());
	}
	
	//método que irá inserir registros responsáveis para validação dos testes
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
	}
}
