package com.teuservico.backTeuServico.agendamento.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AgendamentoRequestDTO {
    private UUID clienteId;
    private UUID profissionalId;
    private Long tipoServicoId;
    private LocalDateTime dataHora;
    private String observacoes;
}