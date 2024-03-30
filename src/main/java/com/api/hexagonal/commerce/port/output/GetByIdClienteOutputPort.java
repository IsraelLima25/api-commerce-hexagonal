package com.api.hexagonal.commerce.port.output;

import java.util.Optional;

import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;

public interface GetByIdClienteOutputPort {

	Optional<ClienteResponse> getValues(String id);
	
}
