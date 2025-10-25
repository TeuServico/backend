package com.teuServico.backTeuServico.appUsuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO para criação de cliente, contendo credenciais de acesso e dados pessoais")
public class CriarClienteDTO {

    @Schema(description = "Credenciais de acesso do cliente")
    @Valid
    private CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO;

    @Schema(description = "Dados pessoais e endereço do cliente")
    @Valid
    private ClienteRequestDTO clienteRequestDTO;
}