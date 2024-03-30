package com.api.hexagonal.commerce.adapter.output.database.entities;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_cliente")
public class ClienteEntity {
	
	@Id
	@Column(name = "id")
	@UuidGenerator
	private String id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "cpf", nullable = false)
	private String cpf;
	
	@Deprecated
	public ClienteEntity() { }

	public ClienteEntity(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}
	
	public String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
}
