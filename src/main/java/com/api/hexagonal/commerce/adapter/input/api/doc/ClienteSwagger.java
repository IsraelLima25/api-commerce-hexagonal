package com.api.hexagonal.commerce.adapter.input.api.doc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.hexagonal.commerce.adapter.exception.handler.response.ApiErroResponse;
import com.api.hexagonal.commerce.adapter.input.api.dto.request.ClienteRequest;
import com.api.hexagonal.commerce.adapter.input.api.dto.response.ClienteResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cliente", description = "")
@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Created", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponse.class))
		}),
		@ApiResponse(responseCode = "400", description = "Bad", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErroResponse.class))
		}),
		@ApiResponse(responseCode = "200", description = "Ok", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponse.class))
		}),
})
public interface ClienteSwagger {
	
	@Operation(operationId = "createCliente", summary = "Endpoint responsável por cadastrar cliente", description = "")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created")})
	ResponseEntity<ClienteResponse> create(ClienteRequest clienteRequest, UriComponentsBuilder uriBuilder);
	
	@Operation(operationId = "getClienteById", summary = "Endpoint responsável buscar cliente pelo id", description = "")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok")})
	ResponseEntity<ClienteResponse> getById(String id);
}
