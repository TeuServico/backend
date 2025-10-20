package com.teuServico.backTeuServico.appServicos.dto;

import com.teuServico.backTeuServico.appServicos.model.TipoServico;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoServicoResponseDTO {
    private Long id;
    private String nome;
    private String categoria;

    public TipoServicoResponseDTO() {
    }

    public TipoServicoResponseDTO(TipoServico tipoServico) {
        this.id = tipoServico.getId();
        this.nome = tipoServico.getNome();
        this.categoria = tipoServico.getCategoria();
    }
}
