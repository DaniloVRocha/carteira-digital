package com.danilo.carteira.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private String instituicão;

	@Column(nullable = false)
	private double saldo;

	@Column(nullable = false)
	private double despesas;

	@Column(nullable = false)
	private double receitas;

	@ManyToOne
	@JoinColumn(name = "fk_cliente_id")
	private Cliente cliente;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<OperacaoConta> extrato;

	public Conta() {
		super();
	}

	public Conta(Long id, String instituicão, double saldo, double despesas, double receitas, Cliente cliente,
			List<OperacaoConta> extrato) {
		super();
		this.id = id;
		this.instituicão = instituicão;
		this.saldo = saldo;
		this.despesas = despesas;
		this.receitas = receitas;
		this.cliente = cliente;
		this.extrato = extrato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstituicão() {
		return instituicão;
	}

	public void setInstituicão(String instituicão) {
		this.instituicão = instituicão;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<OperacaoConta> getExtrato() {
		return extrato;
	}

	public void setExtrato(List<OperacaoConta> extrato) {
		this.extrato = extrato;
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
