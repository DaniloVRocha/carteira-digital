package com.danilo.carteira.dto;

import java.io.Serializable;

import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.dto.response.ClienteResponse;

public class ContaDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String instituicao;
	
	private double saldo;

	private double despesas;

	private double receitas;
	
	private boolean mostrarTelaInicial;
	
	private ClienteResponse cliente; 
	
	public ContaDTO(Conta conta) {
		super();
		this.id = conta.getId();
		this.instituicao = conta.getInstituicao();
		this.saldo = conta.getSaldo();
		this.despesas = conta.getDespesas();
		this.receitas = conta.getReceitas();
		this.cliente = new ClienteResponse(conta.getCliente());
		this.mostrarTelaInicial = conta.getMostrarTelaInicial();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getinstituicao() {
		return instituicao;
	}

	public void setinstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public double getDespesas() {
		return despesas;
	}

	public void setDespesas(double despesas) {
		this.despesas = despesas;
	}

	public double getReceitas() {
		return receitas;
	}

	public void setReceitas(double receitas) {
		this.receitas = receitas;
	}

	public ClienteResponse getCliente() {
		return cliente;
	}

	public void setCliente(ClienteResponse cliente) {
		this.cliente = cliente;
	}

	public boolean isMostrarTelaInicial() {
		return mostrarTelaInicial;
	}

	public void setMostrarTelaInicial(boolean mostrarTelaInicial) {
		this.mostrarTelaInicial = mostrarTelaInicial;
	}
	
}
