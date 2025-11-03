package com.teuServico.backTeuServico.appServicos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "Dados para criação de uma oferta de servico")
@Getter
@Setter
public class OfertaServicoRequestDTO {
    @Schema(description = "Identificado do tipo de servico(que contem nome e categoria)", example = "1")
    @NotNull(message = "tipoServicoId é inválido")
    @Positive(message = "tipoServicoId nao deve ser menor que 1")
    private Long tipoServicoId;

    @Schema(description = "Descrição da oferta de serviço", example = "Desenvolvimento de uma página web utilizando Java e React")
    @NotBlank(message = "descricao é inválido")
    private String descricao;

    @Schema(description = "Tags de uma oferta de servico",example = "[\"Java\", \"Spring\", \"PostgreSQL\", \"React\", \"AWS S3\"]")
    @NotEmpty(message = "tags é inválido")
    private List<String> tags;
}