package com.api.hexagonal.commerce.adapter.output.database.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.hexagonal.commerce.adapter.input.api.dto.mapper.ClienteMapper;
import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.adapter.output.database.entities.ClienteEntity;
import com.api.hexagonal.commerce.adapter.output.database.repository.ClienteRepository;
import com.api.hexagonal.commerce.domain.model.Cliente;
import com.api.hexagonal.commerce.port.output.CreateClienteOutputPort;

@Component
public class CreateClientePersistence implements CreateClienteOutputPort {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateClientePersistence.class);
	
	private final ClienteRepository clienteRepository;
	private final ClienteMapper clienteMapper;
	
	public CreateClientePersistence(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
		this.clienteRepository = clienteRepository;
		this.clienteMapper = clienteMapper;
	}

	@Override
	@Transactional
	public ClienteResponse create(Cliente cliente) {
		
		LOGGER.info("Iniciando persistencia no banco de dados");
		ClienteEntity clienteEntity = new ClienteEntity(cliente.getNome(), cliente.getCpf());
		LOGGER.info("Cliente persistido no banco de dados com sucesso");
		ClienteEntity save = clienteRepository.save(clienteEntity);
		ClienteResponse clienteResponse = clienteMapper.toClienteResponse(save);
		return clienteResponse;
	}
}
