package com.danilo.carteira.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.danilo.carteira.domain.enums.EstadoPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	@Query("SELECT op FROM OperacaoConta op WHERE op.conta.id = :idConta AND op.vencimento BETWEEN :dataInicio AND :dataFim")
	List<OperacaoConta> findOperacaoByDate(Long idConta, LocalDateTime dataInicio, LocalDateTime dataFim);
	
	@Query(value = "SELECT * FROM operacoes WHERE fk_conta_id = ?1 AND EXTRACT(MONTH FROM vencimento) = ?2 AND EXTRACT(YEAR FROM vencimento) = ?3", nativeQuery=true)
	List<OperacaoConta> findOperacaoByMesAno(Long id, Integer numeroMes, Integer numeroAno);
	
	@Query(value = "SELECT SUM(valor) FROM operacoes WHERE fk_conta_id = ?1 AND EXTRACT(MONTH FROM vencimento) = ?2 AND EXTRACT(YEAR FROM vencimento) = ?3 AND TP_OPERACAO = ?4", nativeQuery=true)
	Double findValoresOperacoesMes(Long id, Integer numeroMes, Integer numeroAno, char tpOperacao);
	
	@Query("SELECT op FROM OperacaoConta op WHERE op.conta.id = :idConta AND op.vencimento < :dataVencimento AND op.estadoPagamento = 0")
	List<OperacaoConta> findOperacaoVencidas(Long idConta, LocalDateTime dataVencimento);

	@Query("UPDATE OperacaoConta op SET op.estadoPagamento = :estadoPagamento WHERE op.id = :id AND op.estadoPagamento <> :estadoPagamento")
	@Transactional
	@Modifying
	void informarPagamento(@Param("id") Long id, @Param("estadoPagamento") EstadoPagamento estadoPagamento);

	@Query("SELECT op FROM OperacaoConta op INNER JOIN op.conta con WHERE con.cliente.id = :idCliente AND FUNCTION('MONTH', op.vencimento) = :mes AND FUNCTION('YEAR', op.vencimento) = :ano")
	List<OperacaoConta> buscarOperacoesPorIdCliente(@Param("idCliente") Long id, @Param("mes") Integer numeroMes, @Param("ano") Integer numeroAno);

	@Query(value = "SELECT count(op.categoria) FROM operacoes op INNER JOIN conta con ON con.id = op.fk_conta_id WHERE con.fk_cliente_id = ?1 AND EXTRACT(MONTH FROM op.vencimento) = ?2 AND EXTRACT(YEAR FROM op.vencimento) = ?3 AND op.categoria = ?4", nativeQuery = true)
	public Integer numeroOperacoesPorCategoria(Long id, Integer numeroMes, Integer numeroAno, int categoria);
	
}
