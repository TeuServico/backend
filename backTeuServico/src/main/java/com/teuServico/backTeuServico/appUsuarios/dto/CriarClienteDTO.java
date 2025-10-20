package com.teuServico.backTeuServico.appUsuarios.dto;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarClienteDTO {
    @Valid
    private CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO;
    @Valid
    private ClienteRequestDTO clienteRequestDTO;
}
