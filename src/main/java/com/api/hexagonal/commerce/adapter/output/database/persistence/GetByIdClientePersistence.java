package com.api.hexagonal.commerce.adapter.output.database.persistence;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.api.hexagonal.commerce.adapter.exception.RegystryNotFoundException;
import com.api.hexagonal.commerce.adapter.input.api.dto.mapper.ClienteMapper;
import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.adapter.output.database.entities.ClienteEntity;
import com.api.hexagonal.commerce.adapter.output.database.repository.ClienteRepository;
import com.api.hexagonal.commerce.port.output.GetByIdClienteOutputPort;

@Component
public class GetByIdClientePersistence implements GetByIdClienteOutputPort {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetByIdClientePersistence.class);

	private final ClienteRepository clienteRepository;
	private final ClienteMapper clienteMapper;

	public GetByIdClientePersistence(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
		this.clienteRepository = clienteRepository;
		this.clienteMapper = clienteMapper;
	}

	@Override
	public Optional<ClienteResponse> getValues(String id) {

		LOGGER.info("Consultando banco de dados");
		Optional<ClienteEntity> optionalClienteEntity = clienteRepository.findById(id.toString());
		
		if(optionalClienteEntity.isEmpty()) {
			LOGGER.error("Registro não encontrado no banco de dados");
			throw new RegystryNotFoundException("Registro não encontrado");
		}
		
		LOGGER.info("Registro encontrado no banco de dados");
		ClienteResponse clienteResponse = clienteMapper.toClienteResponse(optionalClienteEntity.get());

		return Optional.of(clienteResponse);
	}
}
