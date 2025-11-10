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

@Getter
@Setter
public class AgendamentoResponseDTO {
    private UUID id;
    private LocalDate dataEntrega;
    private StatusEnum status;
    private String observacoes;
    private BigDecimal precoDesejado;
    private boolean temContraOferta;
    private ContraOferta contraOferta;
    private OfertaServicoResponseDTO ofertaServicoResponseDTO;
    public AgendamentoResponseDTO() {
    }

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

