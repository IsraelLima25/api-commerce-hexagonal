package com.api.hexagonal.commerce.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.domain.model.Cliente;
import com.api.hexagonal.commerce.port.output.CreateClienteOutputPort;

@ExtendWith(SpringExtension.class)
class CreateClienteServiceTest {
	
	private Cliente cliente;
	
	@InjectMocks
	private CreateClienteService createClienteService;
	
	@Mock
	private CreateClienteOutputPort createClienteOutputPort;
	
	@BeforeEach
	void setupInitial() {
		
		cliente = new Cliente("João", "77761781010");
	}
	
	@AfterEach
	void setupFinal() { }
	
	@DisplayName("Should call persistence layer to create cliente")
	@Test
	void shouldCallPersistenceLayerToCreateCliente() {
		
		String idCliente = UUID.randomUUID().toString();
		
		when(createClienteOutputPort.create(cliente)).thenReturn(new ClienteResponse(idCliente, "João", "77761781010"));
		
		ClienteResponse clienteResponse = createClienteService.execute(cliente);
		
		verify(createClienteOutputPort, times(1)).create(cliente);
		
		assertThat(clienteResponse.id()).isEqualTo(idCliente);
		assertThat(clienteResponse.nome()).isEqualTo("João");
		assertThat(clienteResponse.cpf()).isEqualTo("77761781010");
	}
}
