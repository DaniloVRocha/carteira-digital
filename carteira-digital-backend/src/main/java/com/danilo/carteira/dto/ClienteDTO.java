package com.danilo.carteira.dto;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.danilo.carteira.domain.Cliente;

public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "O Nome é Obrigatório.")
	@Length(min = 5, max = 80, message = "O tamanho do nome deve ser entre 5 e 80 caracteres")
	private String nome;
	
	@NotEmpty(message = "O CPF é Obrigatório.")
	@CPF(message = "O CPF Informado é Inválido")
	private String cpf;
	
	@Email
	@NotEmpty(message = "O Email é Obrigatório.")
	private String email;
	
	private Boolean ativo;
	
	private String observacoes;
	
	public ClienteDTO(Cliente cli) {
		super();
		this.id = cli.getId();
		this.nome = cli.getNome();
		this.cpf = cli.getCpf();
		this.email = cli.getEmail();
		this.ativo = cli.getAtivo();
		this.observacoes = cli.getObservacoes();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

}