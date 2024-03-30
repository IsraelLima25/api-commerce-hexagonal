package com.api.hexagonal.commerce.domain.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.port.input.GetByIdClienteInputPort;
import com.api.hexagonal.commerce.port.output.GetByIdClienteOutputPort;

@Service
public class GetByIdClienteService implements GetByIdClienteInputPort {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetByIdClienteService.class);
	
	private final GetByIdClienteOutputPort getByIdClienteOutputPort;

	public GetByIdClienteService(GetByIdClienteOutputPort getByIdClienteOutputPort) {
		this.getByIdClienteOutputPort = getByIdClienteOutputPort;
	}

	@Override
	public Optional<ClienteResponse> execute(String id) {
		
		LOGGER.info("Chamando camada de persistence");
		return getByIdClienteOutputPort.getValues(id);
	}

}
