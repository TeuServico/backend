package com.teuServico.backTeuServico.appServicos.dto;

import com.teuServico.backTeuServico.appServicos.model.TipoServico;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados retornados de um tipo de serviço")
public class TipoServicoResponseDTO {

    @Schema(description = "Identificador único do tipo de serviço", example = "1")
    private Long id;

    @Schema(description = "Nome do tipo de serviço", example = "desenvolver página web")
    private String nome;

    @Schema(description = "Categoria do serviço", example = "programação")
    private String categoria;

    public TipoServicoResponseDTO() {
    }

    public TipoServicoResponseDTO(TipoServico tipoServico) {
        this.id = tipoServico.getId();
        this.nome = tipoServico.getNome();
        this.categoria = tipoServico.getCategoria();
    }
}