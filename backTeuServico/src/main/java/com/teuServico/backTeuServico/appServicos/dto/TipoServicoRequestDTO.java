package com.teuservico.backTeuServico.agendamento.appServicos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoServicoRequestDTO {
    private String nome;
    private String descricao;
    private String categoria;
}
