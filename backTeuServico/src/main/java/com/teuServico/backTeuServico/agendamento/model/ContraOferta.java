package com.teuServico.backTeuServico.agendamento.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ContraOferta {
    private LocalDate ContraOfertaDataDeEntrega;
    private BigDecimal ContraOfertaPrecoDesejado;
}
