package com.api.hexagonal.commerce.adapter.input.api.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.hexagonal.commerce.adapter.input.api.doc.ClienteSwagger;
import com.api.hexagonal.commerce.adapter.input.api.dto.mapper.ClienteMapper;
import com.api.hexagonal.commerce.adapter.input.api.dto.request.ClienteRequest;
import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.domain.model.Cliente;
import com.api.hexagonal.commerce.port.input.CreateClienteInputPort;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/clientes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController implements ClienteSwagger {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);
	
	private final CreateClienteInputPort createClienteInputPort;
	private final ClienteMapper clienteMapper;
	
	public ClienteController(CreateClienteInputPort createClienteInputPort, ClienteMapper clienteMapper) {
		this.createClienteInputPort = createClienteInputPort;
		this.clienteMapper = clienteMapper;
	}

	@PostMapping
	@Override
	public ResponseEntity<ClienteResponse> create(@Valid @RequestBody ClienteRequest clienteRequest, UriComponentsBuilder uriBuilder) {
		
		LOGGER.info("Recebendo operação para cadastrar cliente");
		Cliente cliente = clienteMapper.toCliente(clienteRequest);
		ClienteResponse clienteResponse = createClienteInputPort.execute(cliente);
		URI uri = uriBuilder.path("/v1/clientes/{id}").buildAndExpand(clienteResponse.id()).toUri();
		return ResponseEntity.created(uri).body(clienteResponse);
	}
	
	@GetMapping
	@Override
	public ResponseEntity<ClienteResponse> getById(Long id) {
		
		LOGGER.info("Recebendo operação para buscar cliente por id");
		return null;
	}

}
