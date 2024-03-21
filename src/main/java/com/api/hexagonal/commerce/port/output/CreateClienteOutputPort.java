package com.api.hexagonal.commerce.port.output;

import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.domain.model.Cliente;

public interface CreateClienteOutputPort {

	ClienteResponse create(Cliente cliente);
}
