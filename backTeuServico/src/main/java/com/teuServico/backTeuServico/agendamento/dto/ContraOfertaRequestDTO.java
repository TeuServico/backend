package com.teuServico.backTeuServico.agendamento.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO utilizado para receber os dados da contra-oferta enviada pelo profissional.
 */
@Getter
@Setter
public class ContraOfertaRequestDTO {

    /**
     * Identificador do agendamento ao qual a contra-oferta está vinculada.
     */
    @NotBlank(message = "idDoAgendamento é inválido")
    private String idDoAgendamento;

    /**
     * Nova data de entrega proposta pelo profissional.
     */
    @NotNull(message = "dataEntrega é inválido")
    @Future(message = "dataEntrega deve ser uma data futura.")
    private LocalDate dataEntrega;

    /**
     * Novo preço desejado proposto pelo profissional.
     */
    @NotNull(message = "precoDesejado é inválido")
    @Positive(message = "precoDesejado nao pode ser menor que 1")
    private BigDecimal precoDesejado;
}
