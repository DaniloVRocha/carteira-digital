package com.danilo.carteira.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.dto.response.ClienteResponse;

public class ContaDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String instituicao;
	
	private BigDecimal saldo;

	private BigDecimal despesas;

	private BigDecimal receitas;
	
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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getDespesas() {
		return despesas;
	}

	public void setDespesas(BigDecimal despesas) {
		this.despesas = despesas;
	}

	public BigDecimal getReceitas() {
		return receitas;
	}

	public void setReceitas(BigDecimal receitas) {
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
