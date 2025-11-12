package com.teuServico.backTeuServico.agendamento.model.enums;

/**
 * Enum que representa os possíveis estados de um agendamento.
 */
public enum StatusEnum {

    /**
     * Agendamento aguardando confirmação do profissional.
     */
    AGUARDANDO_CONFIRMACAO_PROFISSIONAL,

    /**
     * Agendamento aguardando confirmação do cliente para uma contra-oferta.
     */
    AGUARDANDO_CONFIRMACAO_CLIENTE,

    /**
     * Agendamento cancelado.
     */
    CANCELADO,

    /**
     * Agendamento concluído.
     */
    CONCLUIDO,

    /**
     * Agendamento em andamento.
     */
    EM_ANDAMENTO
}
