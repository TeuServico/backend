package com.teuServico.backTeuServico.appServicos.dto;

import com.teuServico.backTeuServico.appServicos.model.TipoServico;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para retornar os dados de um tipo de serviço.
 * <p>
 */
@Getter
@Setter
@Schema(description = "Dados retornados de um tipo de serviço")
public class TipoServicoResponseDTO {

    /**
     * Identificador único do tipo de serviço.
     */
    @Schema(description = "Identificador único do tipo de serviço", example = "1")
    private Long id;

    /**
     * Nome do tipo de serviço.
     */
    @Schema(description = "Nome do tipo de serviço", example = "desenvolver página web")
    private String nome;

    /**
     * Categoria à qual o serviço pertence.
     */
    @Schema(description = "Categoria do serviço", example = "programação")
    private String categoria;

    /**
     * Construtor padrão necessário para serialização.
     */
    public TipoServicoResponseDTO() {
    }

    /**
     * Construtor que inicializa o DTO com base na entidade {@link TipoServico}.
     * @param tipoServico entidade contendo os dados do tipo de serviço
     */
    public TipoServicoResponseDTO(TipoServico tipoServico) {
        this.id = tipoServico.getId();
        this.nome = tipoServico.getNome();
        this.categoria = tipoServico.getCategoria();
    }
}