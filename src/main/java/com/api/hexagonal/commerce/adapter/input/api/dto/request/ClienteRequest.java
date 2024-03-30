package com.api.hexagonal.commerce.adapter.input.api.dto.request;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nome", "cpf" })
public record ClienteRequest(
		@NotNull @JsonProperty("nome") String nome,
		@CPF @NotNull @JsonProperty("cpf") String cpf
) { }
