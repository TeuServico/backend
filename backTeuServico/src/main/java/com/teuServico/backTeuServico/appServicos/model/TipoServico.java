package com.project.appService.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tipos_servicos")
public class TipoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;  // Ex: "Corte de Cabelo", "Manicure"

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;  // Preço do serviço

    @Column(nullable = false)
    private Integer duracaoMinutos;  // Duração em minutos

    @Column(nullable = false)
    private Boolean ativo = true;  // Se o serviço está ativo

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    // Construtor vazio (OBRIGATÓRIO)
    public TipoServico() {}

    // Construtor com parâmetros
    public TipoServico(String nome, String descricao, BigDecimal preco, Integer duracaoMinutos) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoMinutos = duracaoMinutos;
    }

    @PrePersist
    protected void aocriar() {
        this.criadoEm = LocalDateTime.now();
    }

    // GETTERS E SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}