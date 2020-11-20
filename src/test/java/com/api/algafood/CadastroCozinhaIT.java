package com.api.algafood;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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

	@LocalServerPort
	private int port; 
	
	@Test
	public void deveRetornarStatus200_quandoConsultarCozinhas() {
		//para ajudar a ver os possíveis problemas no console
		//serve para ver a resposta completa
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
				.get()
			.then()
			.statusCode(HttpStatus.OK.value()); //status ok 200
	}
	
	@Test
	public void deveConter4Cozinhas_quandoConsultarCozinhas() {
		//para ajudar a ver os possíveis problemas no console
		//serve para ver a resposta completa
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
				.get()
			.then()
			.body("", Matchers.hasSize(4))
			.body("nome", Matchers.hasItems("Indiana", "Tailandesa"));
	}
}
