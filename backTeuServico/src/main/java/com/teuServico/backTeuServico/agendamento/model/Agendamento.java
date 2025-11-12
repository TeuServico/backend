package com.teuServico.backTeuServico.agendamento.model;

import com.teuServico.backTeuServico.agendamento.dto.AgendamentoRequestDTO;
import com.teuServico.backTeuServico.agendamento.model.enums.StatusEnum;
import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
/**
 * Entidade que representa um agendamento de serviço entre cliente e profissional.
 */

@Getter
@Setter
@Entity
@Table(name = "agendamentos", indexes = {
        @Index(name = "idx_id_cliente", columnList = "id_cliente"),
        @Index(name = "idx_id_ofertaServico", columnList = "id_oferta_servico")
})
public class Agendamento {

    /**
     * Identificador único do agendamento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Cliente que realizou o agendamento.
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    /**
     * Oferta de serviço selecionada para o agendamento.
     */
    @ManyToOne
    @JoinColumn(name = "id_oferta_servico", nullable = false)
    private OfertaServico ofertaServico;

    /**
     * Data prevista para entrega do serviço.
     */
    @Column(nullable = false)
    private LocalDate dataDeEntrega;

    /**
     * Status atual do agendamento.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    /**
     * Observações adicionais sobre o agendamento.
     */
    @Column(length = 500)
    private String observacoes;

    /**
     * Data em que o agendamento foi solicitado.
     */
    @Column(nullable = false)
    private LocalDate dataSolicitacao;

    /**
     * Preço desejado pelo cliente para o serviço.
     */
    @Column(nullable = false)
    private BigDecimal precoDesejado;

    /**
     * Indica se o agendamento possui uma contra-oferta.
     */
    @Column
    private boolean temContraOferta;

    /**
     * Dados da contra-oferta associada ao agendamento.
     */
    @Embedded
    private ContraOferta contraOferta;

    /**
     * Construtor padrão.
     */
    public Agendamento() {}

    /**
     * Construtor que inicializa o agendamento com base nos dados recebidos via DTO.
     * @param agendamentoRequestDTO dados da solicitação de agendamento
     * @param cliente cliente que está solicitando o agendamento
     * @param ofertaServico oferta de serviço selecionada
     */
    public Agendamento(AgendamentoRequestDTO agendamentoRequestDTO, Cliente cliente, OfertaServico ofertaServico) {
        this.dataDeEntrega = agendamentoRequestDTO.getDataEntrega();
        this.observacoes = agendamentoRequestDTO.getObservacoes();
        this.precoDesejado = agendamentoRequestDTO.getPrecoDesejado();
        this.cliente = cliente;
        this.ofertaServico = ofertaServico;
    }

    /**
     * Método executado automaticamente antes da persistência do agendamento.
     * Define a data de solicitação e o status inicial.
     */
    @PrePersist
    protected void aocriar() {
        this.dataSolicitacao = LocalDate.now();
        if (this.status == null) {
            this.status = StatusEnum.AGUARDANDO_CONFIRMACAO_PROFISSIONAL;
        }
    }

}