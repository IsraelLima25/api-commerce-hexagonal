package com.api.hexagonal.commerce.adapter.input.api.controller;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.api.hexagonal.commerce.adapter.input.api.dto.mapper.ClienteMapperImpl;
import com.api.hexagonal.commerce.adapter.input.api.dto.request.ClienteRequest;
import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.port.input.CreateClienteInputPort;
import com.api.hexagonal.commerce.port.input.GetByIdClienteInputPort;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = { ClienteController.class })
class ClienteControllerTest {

	private static final String URI = "/v1/clientes";

	private static ObjectMapper objectMapper;

	private static final String CPF_INVALID = "00061361010";
	private static final String CPF_VALID = "77761781010";

	private ClienteRequest clienteRequestCpfValid;
	private ClienteRequest clienteRequestCpfInvalid;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CreateClienteInputPort createClienteInputPort;

	@MockBean
	private GetByIdClienteInputPort getByIdClienteInputPort;

	@SpyBean
	private ClienteMapperImpl clienteMapper;

	@BeforeAll
	static void setupBeforeClass() {
		objectMapper = new ObjectMapper();
	}

	@BeforeEach
	void setupInitial() {

		clienteRequestCpfValid = new ClienteRequest("Joao", CPF_VALID);
		clienteRequestCpfInvalid = new ClienteRequest("Maria", CPF_INVALID);
	}

	@AfterEach
	void setupFinal() {	}

	@DisplayName("Should return status 201 when send valid request")
	@Test
	void shouldReturnStatus201WhenSendValidRequest() throws Exception {

		when(createClienteInputPort.execute(any())).thenReturn(new ClienteResponse(UUID.randomUUID().toString(),
				clienteRequestCpfValid.nome(), clienteRequestCpfValid.cpf()));

		mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clienteRequestCpfValid)))
				.andDo(print())
				.andExpect(jsonPath("$.nome", is("Joao")))
				.andExpect(jsonPath("$.cpf", is("77761781010")))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
	}

	@DisplayName("Should return status 400 when field nome is null")
	@Test
	void shoudlReturnStatus400WhenFieldNomeIsNull() throws Exception {

		ClienteRequest clienteRequestNomeIsNull = new ClienteRequest(null, CPF_VALID);

		mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clienteRequestNomeIsNull)))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@DisplayName("Should return status 400 when field cpf is null")
	@Test
	void shouldReturnStatus400WhenFieldCpfIsNull() throws Exception {

		ClienteRequest clienteRequestCpfIsNull = new ClienteRequest("Joao", null);

		mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clienteRequestCpfIsNull)))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@DisplayName("Should return status 400 when field cpf is invalid")
	@Test
	void shouldReturnStatus400WhenFieldCpfIsInvalid() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clienteRequestCpfInvalid)))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@DisplayName("Should return status 200 and object cliente when find id exists")
	@Test
	void shoudlReturnStatus200AndObjectClienteWhenFindIdExists() throws Exception {	
		
		when(getByIdClienteInputPort.execute(any())).thenReturn(Optional.of(new ClienteResponse(UUID.randomUUID().toString(),
				clienteRequestCpfValid.nome(), clienteRequestCpfValid.cpf())));
		
		mockMvc.perform(MockMvcRequestBuilders.get(URI.concat("/{cpf}"), CPF_VALID).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.nome", is("Joao")))
				.andExpect(jsonPath("$.cpf", is("77761781010")))
				.andExpect(status().isOk());
	}
}
