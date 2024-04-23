package com.danilo.carteira.dto.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ContaRequest {
	
	@NotBlank
	@NotNull
	private String instituicao;
	@Column(nullable = false)
	private double saldo;
	@NotNull
	private boolean mostrarTelaInicial;

	public ContaRequest(String instituicao, boolean mostrarTelaInicial, double saldo) {
		super();
		this.instituicao = instituicao;
		this.mostrarTelaInicial = mostrarTelaInicial;
		this.saldo = saldo;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public boolean getMostrarTelaInicial() {
		return mostrarTelaInicial;
	}

	public void setMostrarTelaInicial(boolean mostrarTelaInicial) {
		this.mostrarTelaInicial = mostrarTelaInicial;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
}
