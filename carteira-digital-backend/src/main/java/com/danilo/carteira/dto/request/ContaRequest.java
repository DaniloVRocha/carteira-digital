package com.danilo.carteira.dto.request;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

public class ContaRequest {
	
	@NotBlank
	@NotNull
	private String instituicao;
	
	@NotNull
	private boolean mostrarTelaInicial;

	public ContaRequest(String instituicao, boolean mostrarTelaInicial) {
		super();
		this.instituicao = instituicao;
		this.mostrarTelaInicial = mostrarTelaInicial;
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

}
