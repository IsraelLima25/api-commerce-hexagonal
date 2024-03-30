package com.api.hexagonal.commerce.adapter.input.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.hexagonal.commerce.adapter.input.api.dto.request.ClienteRequest;
import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.adapter.output.database.entities.ClienteEntity;
import com.api.hexagonal.commerce.domain.model.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
	
	@Mapping(target = "nome", source = "clienteRequest.nome")
	@Mapping(target = "cpf", source = "clienteRequest.cpf")
	Cliente toCliente(ClienteRequest clienteRequest);
	
	@Mapping(target = "id", source = "clienteEntity.id")
	@Mapping(target = "nome", source = "clienteEntity.nome")
	@Mapping(target = "cpf", source = "clienteEntity.cpf")
	ClienteResponse toClienteResponse(ClienteEntity clienteEntity);
}
