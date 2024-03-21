package com.api.hexagonal.commerce.adapter.input.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "nome", "cpf" })
public record ClienteResponse(
		@JsonIgnore @JsonProperty("id") String id,
		@JsonProperty("nome") String nome,
		@JsonProperty("cpf") String cpf
) { }
