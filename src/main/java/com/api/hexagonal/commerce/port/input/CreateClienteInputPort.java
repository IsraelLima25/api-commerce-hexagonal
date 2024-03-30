package com.api.hexagonal.commerce.port.input;

import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;
import com.api.hexagonal.commerce.domain.model.Cliente;

public interface CreateClienteInputPort {
	
	ClienteResponse execute(Cliente cliente);
}
