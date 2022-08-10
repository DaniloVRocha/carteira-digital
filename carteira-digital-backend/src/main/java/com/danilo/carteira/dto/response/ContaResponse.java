package com.danilo.carteira.dto.response;

public class ContaResponse {

	private Long id;

	private String instituicao;
	
	private double despesas;

	private double receitas;

	private double saldo;
	
	private boolean mostrarTelaInicial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
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

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public boolean isMostrarTelaInicial() {
		return mostrarTelaInicial;
	}

	public void setMostrarTelaInicial(boolean mostrarTelaInicial) {
		this.mostrarTelaInicial = mostrarTelaInicial;
	}
	
	
}
