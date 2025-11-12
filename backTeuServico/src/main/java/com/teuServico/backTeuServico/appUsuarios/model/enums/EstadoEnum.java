package com.teuServico.backTeuServico.appUsuarios.model.enums;

import lombok.Getter;

/**
 * Enum que representa os estados brasileiros (UFs).
 * <p>
 * Cada constante possui uma sigla (como SP, RJ, PE) e o nome completo do estado correspondente.
 * Utilizado para padronizar o campo {@code estado} na entidade {@link com.teuServico.backTeuServico.appUsuarios.model.Endereco}.
 */
@Getter
public enum EstadoEnum {

    /** Acre */
    AC("Acre"),

    /** Alagoas */
    AL("Alagoas"),

    /** Amapá */
    AP("Amapá"),

    /** Amazonas */
    AM("Amazonas"),

    /** Bahia */
    BA("Bahia"),

    /** Ceará */
    CE("Ceará"),

    /** Distrito Federal */
    DF("Distrito Federal"),

    /** Espírito Santo */
    ES("Espírito Santo"),

    /** Goiás */
    GO("Goiás"),

    /** Maranhão */
    MA("Maranhão"),

    /** Mato Grosso */
    MT("Mato Grosso"),

    /** Mato Grosso do Sul */
    MS("Mato Grosso do Sul"),

    /** Minas Gerais */
    MG("Minas Gerais"),

    /** Pará */
    PA("Pará"),

    /** Paraíba */
    PB("Paraíba"),

    /** Paraná */
    PR("Paraná"),

    /** Pernambuco */
    PE("Pernambuco"),

    /** Piauí */
    PI("Piauí"),

    /** Rio de Janeiro */
    RJ("Rio de Janeiro"),

    /** Rio Grande do Norte */
    RN("Rio Grande do Norte"),

    /** Rio Grande do Sul */
    RS("Rio Grande do Sul"),

    /** Rondônia */
    RO("Rondônia"),

    /** Roraima */
    RR("Roraima"),

    /** Santa Catarina */
    SC("Santa Catarina"),

    /** São Paulo */
    SP("São Paulo"),

    /** Sergipe */
    SE("Sergipe"),

    /** Tocantins */
    TO("Tocantins");

    /**
     * Nome completo do estado.
     */
    private final String nomeCompleto;

    /**
     * Construtor do enum que associa a sigla ao nome completo do estado.
     *
     * @param nomeCompleto nome completo do estado
     */
    EstadoEnum(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
}