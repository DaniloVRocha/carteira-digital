package com.danilo.carteira.dto;

public class OperacaoPorDataDTO {

	private String dataInicial;
	private String dataFinal;

	public OperacaoPorDataDTO() {
		super();
	}

	public OperacaoPorDataDTO(String dataInicial, String dataFinal) {
		super();
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

}
