package com.teuservico.backTeuServico.agendamento.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AgendamentoResponseDTO {
    private Long id;
    private UUID clienteId;
    private String clienteNome;
    private UUID profissionalId;
    private String profissionalNome;
    private Long tipoServicoId;
    private String tipoServicoNome;
    private LocalDateTime dataHora;
    private String status;
    private String observacoes;

    public AgendamentoResponseDTO() {
    }

    public AgendamentoResponseDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.clienteId = agendamento.getCliente().getId();
        this.clienteNome = agendamento.getCliente().getNomeCompleto();
        this.profissionalId = agendamento.getProfissional().getId();
        this.profissionalNome = agendamento.getProfissional().getNomeCompleto();
        this.tipoServicoId = agendamento.getTipoServico().getId();
        this.tipoServicoNome = agendamento.getTipoServico().getNome();
        this.dataHora = agendamento.getDataHora();
        this.status = agendamento.getStatus();
        this.observacoes = agendamento.getObservacoes();
    }
}

