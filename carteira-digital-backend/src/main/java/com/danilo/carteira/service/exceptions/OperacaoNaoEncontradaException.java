package com.danilo.carteira.service.exceptions;

public class OperacaoNaoEncontradaException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public OperacaoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public OperacaoNaoEncontradaException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
