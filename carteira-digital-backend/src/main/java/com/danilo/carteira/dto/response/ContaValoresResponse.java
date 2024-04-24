package com.danilo.carteira.dto.response;

import java.math.BigDecimal;

public class ContaValoresResponse {

	private BigDecimal saldo;

	private BigDecimal despesas;

	private BigDecimal receitas;

	public ContaValoresResponse(BigDecimal saldo, BigDecimal despesas, BigDecimal receitas) {
		super();
		this.saldo = saldo;
		this.despesas = despesas;
		this.receitas = receitas;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getDespesas() {
		return despesas;
	}

	public void setDespesas(BigDecimal despesas) {
		this.despesas = despesas;
	}

	public BigDecimal getReceitas() {
		return receitas;
	}

	public void setReceitas(BigDecimal receitas) {
		this.receitas = receitas;
	}

}
