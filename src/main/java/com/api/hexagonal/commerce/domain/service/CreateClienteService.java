package com.api.hexagonal.commerce.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.domain.model.Cliente;
import com.api.hexagonal.commerce.port.input.CreateClienteInputPort;
import com.api.hexagonal.commerce.port.output.CreateClienteOutputPort;

@Service
public class CreateClienteService implements CreateClienteInputPort {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateClienteService.class);

	private final CreateClienteOutputPort createClienteOutputPort;
	
	public CreateClienteService(CreateClienteOutputPort createClienteOutputPort) {
		this.createClienteOutputPort = createClienteOutputPort;
	}

	@Override
	public ClienteResponse execute(Cliente cliente) {
		LOGGER.info("Chamando camada de persistencia");
		return createClienteOutputPort.create(cliente);
	}
	
}
