package com.danilo.carteira.dto;

public class ContaEdicaoDTO {

	private String instituicao;

	private boolean mostrarTelaInicial;

	public ContaEdicaoDTO(String instituicao, boolean mostrarTelaInicial) {
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
