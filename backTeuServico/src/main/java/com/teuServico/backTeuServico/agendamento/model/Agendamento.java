package com.teuServico.backTeuServico.agendamento.model;

import com.teuServico.backTeuServico.agendamento.dto.AgendamentoRequestDTO;
import com.teuServico.backTeuServico.agendamento.model.enums.StatusEnum;
import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agendamentos", indexes = {
        @Index(name = "idx_id_cliente", columnList = "id_cliente"),
        @Index(name = "idx_id_ofertaServico", columnList = "id_oferta_servico")
})
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_oferta_servico", nullable = false)
    private OfertaServico ofertaServico;

    @Column(nullable = false)
    private LocalDate dataDeEntrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    @Column(length = 500)
    private String observacoes;

    @Column(nullable = false)
    private LocalDate dataSolicitacao;

    @Column(nullable = false)
    private BigDecimal precoDesejado;

    @Column
    private boolean temContraOferta;

    @Embedded
    private ContraOferta contraOferta;

    public Agendamento() {}

    public Agendamento(AgendamentoRequestDTO agendamentoRequestDTO, Cliente cliente, OfertaServico ofertaServico) {
        this.dataDeEntrega = agendamentoRequestDTO.getDataEntrega();
        this.observacoes = agendamentoRequestDTO.getObservacoes();
        this.precoDesejado = agendamentoRequestDTO.getPrecoDesejado();
        this.cliente = cliente;
        this.ofertaServico = ofertaServico;
    }

    @PrePersist
    protected void aocriar() {
        this.dataSolicitacao = LocalDate.now();
        if (this.status == null) {
            this.status = StatusEnum.AGUARDANDO_CONFIRMACAO_PROFISSIONAL;
        }
    }

}
