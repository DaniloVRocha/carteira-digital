package com.danilo.carteira.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContaTransferenciaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idOrigem;

	private Long idDestino;

	private BigDecimal valor;

	public ContaTransferenciaDTO() {
		super();
	}

	public ContaTransferenciaDTO(Long idOrigem, Long idDestino, BigDecimal valor) {
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
