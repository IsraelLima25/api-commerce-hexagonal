package com.api.hexagonal.commerce.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.port.input.GetByIdClienteInputPort;
import com.api.hexagonal.commerce.port.output.GetByIdClienteOutputPort;

@ExtendWith(SpringExtension.class)
class GetByIdClienteServiceTest {

	@InjectMocks
	private GetByIdClienteService getByIdClienteService;

	@Mock
	private GetByIdClienteOutputPort getByIdClienteOutputPort;

	@DisplayName("Should call persistence layer to find cliente")
	@Test
	void shouldCallPersistenceLayerToFindCliente() {

		String idCliente = UUID.randomUUID().toString();

		when(getByIdClienteService.execute(idCliente))
				.thenReturn(Optional.of(new ClienteResponse(idCliente, "João", "77761781010")));

		Optional<ClienteResponse> optionalClienteResponse = getByIdClienteService.execute(idCliente);

		verify(getByIdClienteOutputPort, times(1)).getValues(idCliente);

		ClienteResponse clienteResponse = optionalClienteResponse.get();

		assertThat(clienteResponse.id()).isEqualTo(idCliente);
		assertThat(clienteResponse.nome()).isEqualTo("João");
		assertThat(clienteResponse.cpf()).isEqualTo("77761781010");
	}
}