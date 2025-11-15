package com.teuServico.backTeuServico.agendamento.dto;

import com.teuServico.backTeuServico.agendamento.model.Agendamento;
import com.teuServico.backTeuServico.agendamento.model.ContraOferta;
import com.teuServico.backTeuServico.agendamento.model.enums.StatusEnum;
import com.teuServico.backTeuServico.appServicos.dto.OfertaServicoResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO utilizado para retornar os dados de um agendamento ao cliente ou profissional.
 */
@Getter
@Setter
public class AgendamentoResponseDTO {

    /**
     * Identificador único do agendamento.
     */
    private UUID id;

    /**
     * Data prevista para entrega do serviço.
     */
    private LocalDate dataEntrega;

    /**
     * Status atual do agendamento.
     */
    private StatusEnum status;

    /**
     * Observações adicionais sobre o agendamento.
     */
    private String observacoes;

    /**
     * Preço desejado pelo cliente para o serviço.
     */
    private BigDecimal precoDesejado;

    /**
     * Indica se o agendamento possui uma contra-oferta.
     */
    private boolean temContraOferta;

    /**
     * Dados da contra-oferta associada ao agendamento.
     */
    private ContraOferta contraOferta;

    /**
     * Dados da oferta de serviço vinculada ao agendamento.
     */
    private OfertaServicoResponseDTO ofertaServicoResponseDTO;

    /**
     * Construtor padrão.
     */
    public AgendamentoResponseDTO() {
    }

    /**
     * Construtor que inicializa o DTO com base na entidade {@link Agendamento}.
     * @param agendamento entidade de origem
     */
    public AgendamentoResponseDTO(Agendamento agendamento) {
        this.ofertaServicoResponseDTO = new OfertaServicoResponseDTO(agendamento.getOfertaServico());
        this.id = agendamento.getId();
        this.dataEntrega = agendamento.getDataDeEntrega();
        this.status = agendamento.getStatus();
        this.observacoes = agendamento.getObservacoes();
        this.precoDesejado = agendamento.getPrecoDesejado();
        this.temContraOferta = agendamento.isTemContraOferta();
        this.contraOferta = agendamento.getContraOferta();
    }
}

