package com.teuServico.backTeuServico.agendamento.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Embeddable
public class ContraOferta {
    private LocalDate contraOfertaDataDeEntrega;
    private BigDecimal contraOfertaPrecoDesejado;
}
