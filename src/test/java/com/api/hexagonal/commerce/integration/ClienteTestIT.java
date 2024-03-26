package com.api.hexagonal.commerce.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.api.hexagonal.commerce.util.DataBaseCleaner;
import com.api.hexagonal.commerce.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class ClienteTestIT {
	
	private String jsonClienteValid;
	
	@Autowired
	DataBaseCleaner dataBaseCleaner;
	
	@LocalServerPort
    private int port;
	
	@BeforeEach
	void setupInitial() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/v1/clientes";
        
        jsonClienteValid = ResourceUtils.getContentFromResource(
                "/json/cliente-cpf-valid.json");
        
	}
	
	@AfterEach
	void setupFinal() {
		dataBaseCleaner.clearTables();
	}
	
	@DisplayName("Should save cliente and return status 201 when cliente save call")
	@Test
	void shouldSaveClienteAndReturnStatus201WhenClienteSaveCall() {
		
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonClienteValid)
		.when()
			.post()
				.then()
					.statusCode(HttpStatus.CREATED.value());
				
	}
}
