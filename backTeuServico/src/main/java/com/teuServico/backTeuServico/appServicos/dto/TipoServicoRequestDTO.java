package com.teuServico.backTeuServico.appServicos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para criação de um novo tipo de serviço.
 * <p>
 */
@Getter
@Setter
@Schema(description = "Dados para criação de um tipo de serviço")
public class TipoServicoRequestDTO {

    /**
     * Nome do tipo de serviço.
     * Campo obrigatorio de no máximo 100 caracteres.
     */
    @Schema(description = "Nome do tipo de serviço", example = "Desenvolver página web")
    @NotBlank(message = "nome é inválido")
    @Size(max = 100, message = "nome deve ter no máximo 100 caracteres")
    private String nome;

    /**
     * Categoria à qual o serviço pertence.
     * Campo obrigatorio de no máximo 30 caracteres.
     */
    @Schema(description = "Categoria do serviço", example = "PROGRAMAÇÃO")
    @NotBlank(message = "categoria é inválido")
    @Size(max = 30, message = "categoria deve ter no máximo 30 caracteres")
    private String categoria;
}
