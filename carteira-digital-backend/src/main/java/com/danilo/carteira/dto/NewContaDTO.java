package com.danilo.carteira.dto;

import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.domain.Conta;

public class NewContaDTO {
	private Long id;

	private String instituicao;

	private Cliente cliente;

	public NewContaDTO(Conta conta) {
		super();
		this.id = conta.getId();
		this.instituicao = conta.getInstituicao();
		this.cliente = conta.getCliente();
	}

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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
