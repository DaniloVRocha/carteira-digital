package com.danilo.carteira.dto;

import javax.validation.constraints.NotEmpty;

import com.danilo.carteira.domain.Conta;

public class NewContaDTO {
	private Long id;
	@NotEmpty(message = "O Campo Instituição é Obrigatório.")
	private String instituicao;
	@NotEmpty(message = "O Campo de Cliente deve ser preenchido")
	private ClienteDTO cliente;

	public NewContaDTO(Conta conta) {
		super();
		this.id = conta.getId();
		this.instituicao = conta.getInstituicao();
		this.cliente = new ClienteDTO(conta.getCliente());
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

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

}
