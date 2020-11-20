package com.api.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
//para levantar contexto do spring e deixar fazer uma requisição real
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {
	
	//rodar o flyway antes da execução de cada teste, para termos certeza do estado
	//da massa dos dados testados
	@Autowired
	private Flyway flyway;
	

	@LocalServerPort
	private int port; 
	
	//preparação para os testes funcionarem
	@BeforeEach
	public void setUp() {
		//para ajudar a ver os possíveis problemas no console
		//serve para ver a resposta completa
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath ="/cozinhas";
		
		flyway.migrate();
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
	public void deveConter4Cozinhas_quandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
				.get()
			.then()
			.body("", hasSize(4));
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
}
