package com.danilo.carteira.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.domain.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	
	@Transactional(readOnly=true)
	Page<Conta> findByCliente(Cliente cliente, Pageable pageRequest);
	
	@Query("SELECT c FROM Conta c WHERE fk_cliente_id = ?1")
	List<Conta> findContasByIdCliente(Long id);
	
	@Query(value = "SELECT count(id) FROM OPERACOES WHERE tp_operacao = ?1 AND fk_conta_id = ?2 ", nativeQuery = true)
	public Integer numeroOperacoesPorConta(char tpOperacao, Long idConta);

}
