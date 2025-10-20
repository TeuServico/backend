package com.teuServico.backTeuServico.agendamento.dto;

import com.teuServico.backTeuServico.agendamento.model.Agendamento;
import com.teuServico.backTeuServico.appServicos.dto.OfertaServicoResponseDTO;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AgendamentoResponseDTO {
    private UUID id;
    private LocalDateTime dataEntrega;
    private String status;
    private String observacoes;
    private OfertaServicoResponseDTO ofertaServicoResponseDTO;

    public AgendamentoResponseDTO() {
    }

    public AgendamentoResponseDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.dataEntrega = agendamento.getDataDeEntrega();
        this.status = agendamento.getStatus();
        this.observacoes = agendamento.getObservacoes();
        this.ofertaServicoResponseDTO = new OfertaServicoResponseDTO(agendamento.getOfertaServico());
    }
}

