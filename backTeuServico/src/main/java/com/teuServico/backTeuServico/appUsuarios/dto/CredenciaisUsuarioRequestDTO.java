package com.teuServico.backTeuServico.appUsuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredenciaisUsuarioRequestDTO {
    @Email(message = "email é inválido")
    private String email;

    @NotBlank(message = "senha é inválida")
    private String senha;
}