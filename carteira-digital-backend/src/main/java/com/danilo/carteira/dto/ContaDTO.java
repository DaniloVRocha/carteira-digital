package com.danilo.carteira.dto;

import java.io.Serializable;

import com.danilo.carteira.domain.Conta;

public class ContaDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String instituicão;

	private double saldo;

	private double despesas;

	private double receitas;
	
	
	
	public ContaDTO(Conta conta) {
		super();
		this.id = conta.getId();
		this.instituicão = conta.getInstituicão();
		this.saldo = conta.getSaldo();
		this.despesas = 0;
		this.receitas = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstituicão() {
		return instituicão;
	}

	public void setInstituicão(String instituicão) {
		this.instituicão = instituicão;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public double getDespesas() {
		return despesas;
	}

	public void setDespesas(double despesas) {
		this.despesas = despesas;
	}

	public double getReceitas() {
		return receitas;
	}

	public void setReceitas(double receitas) {
		this.receitas = receitas;
	}
}
