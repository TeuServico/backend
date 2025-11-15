package com.teuServico.backTeuServico.agendamento.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa os dados de uma contra-oferta feita pelo profissional em resposta ao agendamento.
 */
@Getter
@Setter
@Embeddable
public class ContraOferta {

    /**
     * Nova data de entrega proposta pelo profissional.
     */
    private LocalDate contraOfertaDataDeEntrega;

    /**
     * Novo preço desejado proposto pelo profissional.
     */
    private BigDecimal contraOfertaPrecoDesejado;
}
