package com.danilo.carteira.domain.enums;

public enum Categoria {

	SEM_CATEGORIA(0, "Sem Categoria"),
	ALIMENTACAO(1, "Alimentação"),
	SAUDE(2, "Saúde"),
	LAZER(3, "Lazer"),
	FIXO(4, "Fixo"),
	EDUCACAO(5, "Educação"),
	INVESTIMENTO(6, "Investimento"),
	IMPOSTOS(7, "Impostos"),
	CASA(8, "Casa"),
	TRANSPORTE(9, "Transporte"),
	GASTOS_PESSOAIS(10, "Gastos Pessoais"),
	AJUSTE(11, "Ajuste de Saldo");

	private int cod;
	private String descricao;


	private Categoria(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Categoria toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Categoria x : Categoria.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id Invalido: " + cod);
	}

}

