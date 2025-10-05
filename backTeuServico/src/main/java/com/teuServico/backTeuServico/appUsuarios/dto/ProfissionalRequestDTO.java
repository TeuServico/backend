package com.teuServico.backTeuServico.appUsuarios.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfissionalRequestDTO {
    private String nomeCompleto;
    private String email;
    private String telefone;
    private String cpf;
    private String profissao;
}