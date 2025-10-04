package com.teuservico.backTeuServico.agendamento.appServicos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoServicoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String categoria;

    public TipoServicoResponseDTO() {
    }

    public TipoServicoResponseDTO(TipoServico tipoServico) {
        this.id = tipoServico.getId();
        this.nome = tipoServico.getNome();
        this.descricao = tipoServico.getDescricao();
        this.categoria = tipoServico.getCategoria();
    }
}
