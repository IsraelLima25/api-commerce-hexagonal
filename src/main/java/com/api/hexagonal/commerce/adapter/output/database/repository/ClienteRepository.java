package com.api.hexagonal.commerce.adapter.output.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.hexagonal.commerce.adapter.output.database.entities.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, String> { }
