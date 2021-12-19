package com.danilo.carteira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danilo.carteira.domain.OperacaoConta;

public interface OperacaoContaRepository extends JpaRepository<OperacaoConta, Long> {


}
