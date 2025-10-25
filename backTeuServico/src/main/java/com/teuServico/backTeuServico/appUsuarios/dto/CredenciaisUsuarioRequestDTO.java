package com.teuServico.backTeuServico.appUsuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Credenciais de acesso do usuario")
public class CredenciaisUsuarioRequestDTO {

    @Schema(description = "Email para login", example = "usuario@exemplo.com")
    @NotBlank(message = "email é inválido")
    @Email(message = "email é inválido")
    private String email;

    @Schema(description = "Senha para login", example = "senhaSegura123")
    @NotBlank(message = "senha é inválida")
    private String senha;

}