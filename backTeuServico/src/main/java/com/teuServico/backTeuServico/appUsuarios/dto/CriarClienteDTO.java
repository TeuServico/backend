package com.teuServico.backTeuServico.appUsuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para criação de um novo cliente.
 * <p>
 * Contém as credenciais de acesso e os dados pessoais (incluindo endereço).
 */
@Getter
@Setter
@Schema(description = "DTO para criação de cliente, contendo credenciais de acesso e dados pessoais")
public class CriarClienteDTO {

    /**
     * Credenciais de acesso do cliente (email e senha).
     */
    @Schema(description = "Credenciais de acesso do cliente")
    @Valid
    private CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO;

    /**
     * Dados pessoais e endereço do cliente.
     */
    @Schema(description = "Dados pessoais e endereço do cliente")
    @Valid
    private ClienteRequestDTO clienteRequestDTO;
}