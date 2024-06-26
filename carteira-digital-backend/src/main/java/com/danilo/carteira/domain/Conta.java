package com.danilo.carteira.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String instituicao;

	@Column(nullable = false)
	private BigDecimal saldo;
	private BigDecimal despesas;
	private BigDecimal receitas;
	
	private boolean mostrarTelaInicial;

	@ManyToOne
	@JoinColumn(name = "fk_cliente_id")
	@JsonIgnore
	private Cliente cliente;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "conta")
	@JsonIgnore
	private List<OperacaoConta> operacao;

	public Conta() {
		super();
	}

	public Conta(Long id, String instituicao, BigDecimal saldo, Cliente cliente) {
		super();
		this.id = id;
		this.instituicao = instituicao;
		this.saldo = saldo;
		this.cliente = cliente;
		this.mostrarTelaInicial = true;
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

	public void setInstituicão(String instituicao) {
		this.instituicao = instituicao;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public boolean getMostrarTelaInicial() {
		return mostrarTelaInicial;
	}

	public void setMostrarTelaInicial(boolean mostrarTelaInicial) {
		this.mostrarTelaInicial = mostrarTelaInicial;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<OperacaoConta> getOperacao() {
		return operacao;
	}

	public void setOperacao(List<OperacaoConta> operacao) {
		this.operacao = operacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(id, other.id);
	}
}
