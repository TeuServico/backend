package com.teuServico.backTeuServico.agendamento.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AgendamentoRequestDTO {
    private UUID clienteId;
    private Long ofertaServicoId;
    private LocalDateTime dataEntrega;
    private String observacoes;
}