package com.danilo.carteira.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.danilo.carteira.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "operacoes")
public class OperacaoConta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime dataHora;

	// 'C' para receita e 'D' para despesa
	@Column(length = 1, nullable = false)
	private char tpOperacao;

	@Column(nullable = false)
	private double valor;

	@Column(nullable = false)
	private EstadoPagamento estadoPagamento;

	@ManyToOne
	@JoinColumn(name = "fk_conta_id")
	@JsonIgnore
	private Conta conta;

	@Column(length = 100)
	private String observacao;

	public OperacaoConta() {
		super();
	}

	public OperacaoConta(Long id, LocalDateTime dataHora, char tpOperacao, double valor,
			EstadoPagamento estadoPagamento, Conta conta, String observacao) {
		super();
		this.id = id;
		this.dataHora = dataHora;
		this.tpOperacao = tpOperacao;
		this.valor = valor;
		this.estadoPagamento = estadoPagamento;
		this.conta = conta;
		this.observacao = observacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public char getTpOperacao() {
		return tpOperacao;
	}

	public void setTpOperacao(char tpOperacao) {
		this.tpOperacao = tpOperacao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public EstadoPagamento getEstadoPagamento() {
		return estadoPagamento;
	}

	public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
		this.estadoPagamento = estadoPagamento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperacaoConta other = (OperacaoConta) obj;
		return Objects.equals(id, other.id);
	}
}