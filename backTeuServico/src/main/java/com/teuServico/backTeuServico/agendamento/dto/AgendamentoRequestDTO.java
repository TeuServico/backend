package com.teuServico.backTeuServico.agendamento.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AgendamentoRequestDTO {
    @NotNull(message = "clienteId é inválido")
    private UUID clienteId;

    @NotNull(message = "ofertaServicoId é inválido")
    @Positive(message = "ofertaServicoId nao pode ser menor que 1")
    private Long ofertaServicoId;

    @NotNull(message = "dataEntrega é inválido")
    @Future(message = "dataEntrega deve ser uma data futura.")
    private LocalDateTime dataEntrega;

    @NotBlank(message = "observacoes é inválido")
    private String observacoes;

}