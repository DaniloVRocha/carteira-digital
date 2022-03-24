package com.danilo.carteira.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
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
	private String instituicao;

	@Column(nullable = false)
	private double saldo;

	private double despesas;

	private double receitas;
	
	private boolean mostrarTelaInicial;

	@ManyToOne
	@JoinColumn(name = "fk_cliente_id")
	@JsonIgnore
	private Cliente cliente;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<OperacaoConta> operacao;

	public Conta() {
		super();
	}

	public Conta(Long id, String instituicao, double saldo, Cliente cliente) {
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

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public boolean getMostrarTelaInicial() {
		return mostrarTelaInicial;
	}

	public void setMostrarTelaInicial(boolean mostrarTelaInicial) {
		this.mostrarTelaInicial = mostrarTelaInicial;
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
	
	@Override
	public String toString() {
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		StringBuilder builder = new StringBuilder();
		
		builder.append("Informações Da Conta: ");
		builder.append("\n");
		builder.append("Nome da Conta: " + instituicao );
		builder.append("\n");
		builder.append("Saldo Atual: " + nf.format(saldo));
		builder.append("\n");
		builder.append("Despesas: " + nf.format(despesas));
		builder.append("\n");
		builder.append("Receitas: " + nf.format(receitas));
		builder.append("\n");
		
		return builder.toString();
	
	}
	
}
