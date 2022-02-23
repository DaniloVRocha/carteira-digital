package com.danilo.carteira.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.danilo.carteira.domain.enums.Categoria;
import com.danilo.carteira.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "operacoes")
public class OperacaoConta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime dataHora;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime vencimento;
	
	private Categoria categoria;

	// 'R' para receita e 'D' para despesa
	@Column(length = 1, nullable = false)
	private char tpOperacao;

	@Column(nullable = false)
	private double valor;

	@Column(nullable = false)
	private EstadoPagamento estadoPagamento;

	@ManyToOne
	@JoinColumn(name = "fk_conta_id")
	private Conta conta;

	@Column(length = 100)
	private String observacao;

	public OperacaoConta() {
		super();
	}

	public OperacaoConta(Long id, LocalDateTime dataHora, LocalDateTime vencimento, char tpOperacao, double valor,
			EstadoPagamento estadoPagamento, Conta conta, String observacao, Categoria categoria) {
		super();
		this.id = id;
		this.dataHora = dataHora;
		this.vencimento = vencimento;
		this.tpOperacao = tpOperacao;
		this.valor = valor;
		this.estadoPagamento = estadoPagamento;
		this.conta = conta;
		this.observacao = observacao;
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		StringBuilder builder = new StringBuilder();
		
		builder.append("Uma nova operação foi registrada em sua conta. ");
		builder.append("\n");
		
		builder.append(nf.format(getValor()));
		if (tpOperacao == 'R') {
			builder.append(" foram acrescentados.\n ");
		} else {
			builder.append(" foram debitados.\n ");
		}

		builder.append("Data da Operação: " + getDataHora().format(formatter));	
		
		return builder.toString();
	
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public EstadoPagamento getEstadoPagamento() {
		return estadoPagamento;
	}

	public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
		this.estadoPagamento = estadoPagamento;
	}

	public LocalDateTime getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDateTime vencimento) {
		this.vencimento = vencimento;
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
