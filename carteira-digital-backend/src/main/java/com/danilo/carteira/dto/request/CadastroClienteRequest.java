package com.danilo.carteira.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public class CadastroClienteRequest {
	
	@NotEmpty(message = "O Campo Nome é Obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho do nome deve ser entre 5 e 80 caracteres")
	private String nome;
	
	@NotEmpty(message = "O Campo CPF é Obrigatório")
	@CPF(message = "O CPF informado é inválido")
	private String cpf;
	
	@NotEmpty(message = "O Campo Email é Obrigatório")
	@Email(message = "O Email informado é inválido")
	private String email;
	
	@NotEmpty(message = "O Campo Senha é Obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho da senha deve ser entre 5 e 80 caracteres")
	private String senha;
	
	private Boolean ativo;

	public CadastroClienteRequest(Long id, String nome, String cpf, String email, String senha) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.ativo = true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getAtivo() {
		return ativo;
	}
	
}
