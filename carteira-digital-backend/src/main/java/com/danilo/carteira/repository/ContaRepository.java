package com.danilo.carteira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danilo.carteira.domain.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {


}
