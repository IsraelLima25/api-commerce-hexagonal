package com.api.hexagonal.commerce.integration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.api.hexagonal.commerce.adapter.input.api.dto.request.ClienteRequest;
import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.adapter.output.database.entities.ClienteEntity;
import com.api.hexagonal.commerce.adapter.output.database.repository.ClienteRepository;
import com.api.hexagonal.commerce.util.DataBaseCleaner;
import com.api.hexagonal.commerce.util.ResourceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class ClienteTestIT {
	
	private String jsonClienteValid;
	
	@Autowired
	private DataBaseCleaner dataBaseCleaner;
	
	@Autowired
	private ObjectMapper objMapper;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@LocalServerPort
    private int port;
	
	@BeforeEach
	void setupInitial() {
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/v1/clientes";
        
        jsonClienteValid = ResourceUtils.getContentFromResource(
                "/json/cliente-cpf-valid.json");
   
        prepareDataMass();
	}
	
	@AfterEach
	void setupFinal() {
		
		destroyDataMass();
	}
	
	@DisplayName("Should save cliente, return header location, cliente save and status 201 when cliente save call")
	@Test
	void shouldSaveClienteAndReturnStatus201WhenClienteSaveCall() throws Exception {
		
		ClienteResponse resultClienteResponse = given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonClienteValid)
		.when()
			.post()
				.then()
					.statusCode(HttpStatus.CREATED.value())
					.contentType(ContentType.JSON)
					.header("Location", containsString(RestAssured.basePath))
					.extract().as(ClienteResponse.class);
		
		
		ClienteRequest clienteRequest = objMapper.readValue(jsonClienteValid, ClienteRequest.class);
		
		assertThat(clienteRequest.nome()).isEqualTo(resultClienteResponse.nome());
		assertThat(clienteRequest.cpf()).isEqualTo(resultClienteResponse.cpf());
				
	}
	
	@DisplayName("Should get cliente not exists when return status 404")
	@Test
	void shouldGetClienteNotExistsWhenReturnStatus404() throws Exception {
		
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonClienteValid)
		.when()
			.get("/{id}", UUID.randomUUID().toString())
				.then()
					.statusCode(HttpStatus.NOT_FOUND.value());
				
	}
	
	@DisplayName("Should get cliente exists when return status 404 and cliente body")
	@Test
	void shouldGetClienteExistsWhenReturnClienteBodyAndStatus200() throws Exception {
		
		ClienteEntity clienteEntity = clienteRepository.findByCpf("92691464059");
		
		ClienteResponse clienteResponse = given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonClienteValid)
		.when()
			.get("/{id}", clienteEntity.getId())
				.then()
					.statusCode(HttpStatus.OK.value())
					.extract().as(ClienteResponse.class);
		
		assertThat(clienteEntity.getNome()).isEqualTo(clienteResponse.nome());
		assertThat(clienteEntity.getCpf()).isEqualTo(clienteResponse.cpf());
	}
	
	private void prepareDataMass() {
		
		ClienteEntity cliente1 = new ClienteEntity("Pedro","92691464059");
		ClienteEntity cliente2 = new ClienteEntity("Maria","33349749011");
		ClienteEntity cliente3 = new ClienteEntity("Paulo","56412927026");
		
		clienteRepository.save(cliente1);
		clienteRepository.save(cliente2);
		clienteRepository.save(cliente3);
	}
	
	private void destroyDataMass() {
		dataBaseCleaner.clearTables();
	}
}
