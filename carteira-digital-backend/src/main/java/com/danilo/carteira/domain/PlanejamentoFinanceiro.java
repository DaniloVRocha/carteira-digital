package com.danilo.carteira.domain;

import com.danilo.carteira.domain.enums.Categoria;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "planejamento_financeiro")
public class PlanejamentoFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orcamento_inicial", nullable = false)
    private BigDecimal orcamentoInicial;

    @Column(nullable = false)
    private Integer mes;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "orcamento_por_categoria",
            joinColumns = @JoinColumn(name = "planejamento_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "categoria")
    @Column(name = "orcamento", nullable = false)
    private Map<Categoria, BigDecimal> orcamentoPorCategoria = new HashMap<>();


    public PlanejamentoFinanceiro() {
    }

    public PlanejamentoFinanceiro(Long id, BigDecimal orcamentoInicial, Integer mes, Map<Categoria, BigDecimal> orcamentoPorCategoria) {
        this.id = id;
        this.orcamentoInicial = orcamentoInicial;
        this.mes = mes;
        this.orcamentoPorCategoria = orcamentoPorCategoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getOrcamentoInicial() {
        return orcamentoInicial;
    }

    public void setOrcamentoInicial(BigDecimal orcamentoInicial) {
        this.orcamentoInicial = orcamentoInicial;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Map<Categoria, BigDecimal> getOrcamentoPorCategoria() {
        return orcamentoPorCategoria;
    }

    public void setOrcamentoPorCategoria(Map<Categoria, BigDecimal> orcamentoPorCategoria) {
        this.orcamentoPorCategoria = orcamentoPorCategoria;
    }
}

