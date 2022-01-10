package com.danilo.carteira.dto;

import java.io.Serializable;

public class ContaTransferenciaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idOrigem;

	private Long idDestino;

	private double valor;

	public ContaTransferenciaDTO() {
		super();
	}

	public ContaTransferenciaDTO(Long idOrigem, Long idDestino, double valor) {
		super();
		this.idOrigem = idOrigem;
		this.idDestino = idDestino;
		this.valor = valor;
	}

	public Long getIdOrigem() {
		return idOrigem;
	}

	public void setIdOrigem(Long idOrigem) {
		this.idOrigem = idOrigem;
	}

	public Long getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Long idDestino) {
		this.idDestino = idDestino;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
