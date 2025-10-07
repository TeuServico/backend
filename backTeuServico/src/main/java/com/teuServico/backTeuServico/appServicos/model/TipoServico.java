package com.teuServico.backTeuServico.appServicos.model;

import com.teuServico.backTeuServico.appServicos.dto.TipoServicoRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tipos_servicos")
public class TipoServico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Column(length = 30)
    private String categoria;

    public TipoServico() {}

    public TipoServico(TipoServicoRequestDTO tipoServicoRequestDTO) {
        this.nome = tipoServicoRequestDTO.getNome();
        this.categoria = tipoServicoRequestDTO.getCategoria();
    }

}