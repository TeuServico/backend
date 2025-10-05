package com.teuServico.backTeuServico.appUsuarios.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredenciaisUsuarioRequestDTO {
    private String email;
    private String senha;
}