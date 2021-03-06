package com.danilo.carteira.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.domain.OperacaoConta;

public interface OperacaoContaRepository extends JpaRepository<OperacaoConta, Long> {

	@Transactional(readOnly=true)
	Page<OperacaoConta> findByConta(Conta conta, Pageable pageRequest);

	List<OperacaoConta> findOperacaoByConta(Conta conta);
	
	@Transactional(readOnly=true)
	@Query(value = "SELECT * FROM operacoes op INNER JOIN conta con ON con.id = op.fk_conta_id AND con.fk_cliente_id = ?1 WHERE (op.data_hora BETWEEN ?2 AND ?3)", nativeQuery = true)
	Page<OperacaoConta> pageByCliente(Long id, LocalDateTime dataInicial, LocalDateTime dataFinal, Pageable pageRequest);
	
	@Query("SELECT op FROM OperacaoConta op WHERE fk_conta_id = ?1 AND data_hora BETWEEN ?2 AND ?3")
	List<OperacaoConta> findOperacaoByDate(Long id, LocalDateTime dataInicio, LocalDateTime dataFim);
	
	@Query(value = "SELECT * FROM operacoes WHERE fk_conta_id = ?1 AND MONTH(vencimento) = ?2 AND YEAR(VENCIMENTO) = ?3", nativeQuery=true)
	List<OperacaoConta> findOperacaoByMesAno(Long id, Integer numeroMes, Integer numeroAno);
	
	@Query(value = "SELECT sum(valor) FROM operacoes WHERE fk_conta_id = ?1 AND MONTH(vencimento) = ?2 AND YEAR(VENCIMENTO) = ?3  AND TP_OPERACAO  = ?4", nativeQuery=true)
	Double findValoresOperacoesMes(Long id, Integer numeroMes, Integer numeroAno, char tpOperacao);
	
	@Query("SELECT op FROM OperacaoConta op WHERE fk_conta_id = ?1 AND vencimento < ?2 AND estado_pagamento = 0")
	List<OperacaoConta> findOperacaoVencidas(Long id, LocalDateTime dataAtual);
	
	@Query("UPDATE OperacaoConta op SET estado_pagamento = ?2 WHERE id = ?1 AND estado_pagamento <> ?2")
	@Transactional
	@Modifying
	void informarPagamento(Long id, Long codEstado);
	
	@Query("UPDATE OperacaoConta op SET estado_pagamento = ?2 WHERE id = ?1 AND estado_pagamento <> ?2")
	@Transactional
	@Modifying
	void editrOperacaoConta(OperacaoConta op);
	
	@Query("SELECT op FROM OperacaoConta op INNER JOIN Conta con ON con.id = op.conta.id AND con.cliente.id = ?1 WHERE MONTH(vencimento) = ?2 AND YEAR(VENCIMENTO) = ?3")
	List<OperacaoConta> buscarOperacoesPorId(Long id, Integer numeroMes, Integer numeroAno);
	
	@Query(value = "SELECT count(op.categoria) FROM operacoes op INNER JOIN conta con ON con.id = op.fk_conta_id AND con.fk_cliente_id = ?1 WHERE MONTH(vencimento) = ?2 AND YEAR(VENCIMENTO) = ?3 AND op.categoria=?4", nativeQuery = true)
	public Integer numeroOperacoesPorCategoria(Long id, Integer numeroMes, Integer numeroAno, int categoria);
	
}
