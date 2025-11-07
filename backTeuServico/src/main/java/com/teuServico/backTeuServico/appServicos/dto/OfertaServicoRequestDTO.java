package com.teuServico.backTeuServico.appServicos.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Dados para criação de uma oferta de servico")
@Getter
@Setter
public class OfertaServicoRequestDTO {
    @Schema(description = "Identificado do tipo de servico(que contem nome e categoria). Obrigatório se tipoServicoNome não for informado.", example = "1")
    @Positive(message = "tipoServicoId nao deve ser menor que 1")
    private Long tipoServicoId;

    @Schema(description = "Nome do tipo de serviço. Obrigatório se tipoServicoId não for informado.", example = "Desenvolvimento de Landing pages")
    @jakarta.validation.constraints.Size(max = 100, message = "tipoServicoNome deve ter no máximo 100 caracteres")
    private String tipoServicoNome;

    @Schema(description = "Categoria do tipo de serviço. Obrigatório se tipoServicoNome for informado.", example = "PROGRAMAÇÃO")
    @jakarta.validation.constraints.Size(max = 30, message = "tipoServicoCategoria deve ter no máximo 30 caracteres")
    private String tipoServicoCategoria;

    @Schema(description = "Descrição da oferta de serviço", example = "Desenvolvimento de uma página web utilizando Java e React")
    @NotBlank(message = "descricao é inválido")
    private String descricao;

    @Schema(description = "Tags de uma oferta de servico", example = "[\"Java\", \"Spring\", \"PostgreSQL\", \"React\", \"AWS S3\"]")
    @NotEmpty(message = "tags é inválido")
    private List<String> tags;
}
