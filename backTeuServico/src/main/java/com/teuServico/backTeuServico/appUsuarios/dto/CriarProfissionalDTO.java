package com.teuServico.backTeuServico.appUsuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para criação de um novo profissional.
 * <p>
 * Contém as credenciais de acesso e os dados pessoais (incluindo endereço).
 */
@Getter
@Setter
@Schema(description = "DTO para criação de profissional, contendo credenciais de acesso e dados pessoais")
public class CriarProfissionalDTO {

    /**
     * Credenciais de acesso do profissional (email e senha).
     */
    @Schema(description = "Credenciais de acesso do profissional")
    @Valid
    private CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO;

    /**
     * Dados pessoais e endereço do profissional.
     */
    @Schema(description = "Dados pessoais e endereço do profissional")
    @Valid
    private ProfissionalRequestDTO profissionalRequestDTO;
}