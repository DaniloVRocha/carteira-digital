package com.danilo.carteira.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.domain.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	
	@Transactional(readOnly=true)
	Page<Conta> findByCliente(Cliente cliente, Pageable pageRequest);

}
