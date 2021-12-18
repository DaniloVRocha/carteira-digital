package com.danilo.carteira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danilo.carteira.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
}
