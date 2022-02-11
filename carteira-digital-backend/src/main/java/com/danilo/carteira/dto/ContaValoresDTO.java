package com.danilo.carteira.dto;

public class ContaValoresDTO {

	private double saldo;

	private double despesas;

	private double receitas;

	public ContaValoresDTO(double saldo, double despesas, double receitas) {
		super();
		this.saldo = saldo;
		this.despesas = despesas;
		this.receitas = receitas;
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
