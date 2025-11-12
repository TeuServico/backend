package com.teuServico.backTeuServico.appUsuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para receber as credenciais de acesso do usuário.
 * <p>
 * Contém o email e a senha informados no momento do login.
 */
@Getter
@Setter
@Schema(description = "Credenciais de acesso do usuario")
public class CredenciaisUsuarioRequestDTO {

    /**
     * Email utilizado para autenticação.
     * Campo obrigatório.
     */
    @Schema(description = "Email para login", example = "usuario@exemplo.com")
    @NotBlank(message = "email é inválido")
    @Email(message = "email é inválido")
    private String email;

    /**
     * Senha utilizada para autenticação.
     * Campo obrigatório.
     */
    @Schema(description = "Senha para login", example = "senhaSegura123")
    @NotBlank(message = "senha é inválida")
    private String senha;
}