package com.danilo.carteira.dto;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.danilo.carteira.domain.OperacaoConta;
import com.danilo.carteira.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;

public class OperacaoContaDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime dataHora;

	private char tpOperacao;

	private double valor;

	private EstadoPagamento estadoPagamento;

	private String observacao;

	public OperacaoContaDTO() {
		super();
	}

	public OperacaoContaDTO(OperacaoConta op) {
		super();
		this.id = op.getId();
		this.dataHora = op.getDataHora();
		this.tpOperacao = op.getTpOperacao();
		this.valor = op.getValor();
		this.estadoPagamento = op.getEstadoPagamento();
		this.observacao = op.getObservacao();
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

	public EstadoPagamento getEstadoPagamento() {
		return estadoPagamento;
	}

	public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
		this.estadoPagamento = estadoPagamento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
