package com.teuServico.backTeuServico.appUsuarios.dto;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarProfissionalDTO {
    @Valid
    private CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO;
    @Valid
    private ProfissionalRequestDTO profissionalRequestDTO;
}
