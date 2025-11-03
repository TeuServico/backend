package com.teuServico.backTeuServico.agendamento.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ContraOfertaResponseDTO {
    private String idDoAgendamento;
    private String observacoesDoAgendamento;
    private LocalDate dataDeEntrega;
    private BigDecimal precoDesejado;
}
