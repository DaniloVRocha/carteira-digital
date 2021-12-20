package com.danilo.carteira.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.domain.OperacaoConta;

public interface OperacaoContaRepository extends JpaRepository<OperacaoConta, Long> {

	@Transactional(readOnly=true)
	Page<OperacaoConta> findByConta(Conta conta, Pageable pageRequest);
	
}
