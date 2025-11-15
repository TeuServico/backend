package com.teuServico.backTeuServico.appServicos.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para criação de uma nova oferta de serviço por um profissional.
 * <p>
 * Contém o tipo de serviço, a descrição da oferta e as tags associadas.
 */
@Schema(description = "Dados para criação de uma oferta de servico")
@Getter
@Setter
public class OfertaServicoRequestDTO {

    /**
     * Identificador do tipo de serviço (que contém nome e categoria).
     */
    @Schema(description = "Identificador do tipo de servico(que contem nome e categoria).", example = "1")
    @Positive(message = "tipoServicoId nao deve ser menor que 1")
    private Long tipoServicoId;

    /**
     * Descrição da oferta de serviço.
     */
    @Schema(description = "Descrição da oferta de serviço", example = "Desenvolvimento de uma página web utilizando Java e React")
    @NotBlank(message = "descricao é inválido")
    private String descricao;

    /**
     * Lista de tags associadas à oferta de serviço.
     * Utilizadas para facilitar buscas e categorização.
     */
    @Schema(description = "Tags de uma oferta de servico", example = "[\"Java\", \"Spring\", \"PostgreSQL\", \"React\", \"AWS S3\"]")
    @NotEmpty(message = "tags é inválido")
    private List<String> tags;
}
