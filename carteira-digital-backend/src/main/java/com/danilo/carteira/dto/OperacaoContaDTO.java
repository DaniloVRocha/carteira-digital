package com.danilo.carteira.dto;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.danilo.carteira.domain.OperacaoConta;
import com.danilo.carteira.domain.enums.Categoria;
import com.danilo.carteira.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;

public class OperacaoContaDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime dataHora;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime vencimento;

	private char tpOperacao;

	private double valor;

	private EstadoPagamento estadoPagamento;

	private Categoria categoria;

	private NewContaDTO conta;

	public OperacaoContaDTO() {
		super();
	}

	public OperacaoContaDTO(OperacaoConta op) {
		super();
		this.id = op.getId();
		this.nome = op.getNome();
		this.dataHora = op.getDataHora();
		this.tpOperacao = op.getTpOperacao();
		this.valor = op.getValor();
		this.estadoPagamento = op.getEstadoPagamento();
		this.conta = new NewContaDTO(op.getConta());
		this.vencimento = op.getVencimento();
		this.categoria = op.getCategoria();
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public NewContaDTO getConta() {
		return conta;
	}

	public void setConta(NewContaDTO conta) {
		this.conta = conta;
	}

	public LocalDateTime getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDateTime vencimento) {
		this.vencimento = vencimento;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
