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
 * DTO utilizado para receber os dados da solicitação de agendamento feita pelo cliente.
 */
@Getter
@Setter
public class AgendamentoRequestDTO {
    /**
     * Identificador da oferta de serviço selecionada.
     */
    @NotNull(message = "ofertaServicoId é inválido")
    @Positive(message = "ofertaServicoId nao pode ser menor que 1")
    private Long ofertaServicoId;

    /**
     * Data desejada para entrega do serviço.
     */
    @NotNull(message = "dataEntrega é inválido")
    @Future(message = "dataEntrega deve ser uma data futura.")
    private LocalDate dataEntrega;

    /**
     * Observações adicionais fornecidas pelo cliente.
     */
    @NotBlank(message = "observacoes é inválido")
    private String observacoes;

    /**
     * Preço desejado pelo cliente para o serviço.
     */
    @NotNull(message = "precoDesejado é inválido")
    @Positive(message = "precoDesejado nao pode ser menor que 1")
    private BigDecimal precoDesejado;

}