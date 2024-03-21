package com.api.hexagonal.commerce.port.input;

import java.util.Optional;

import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;

public interface GetByIdClienteInputPort {

	Optional<ClienteResponse> execute(String id);

}
