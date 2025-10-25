package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Token JWT retornado após autenticação")
public class TokenJWT {

    @Schema(description = "Token de acesso JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String acessToken;

    @Schema(description = "Tempo de expiração do token em segundos", example = "3600")
    private Long expiresIn;

    @Schema(description = "Papel do usuário autenticado", example = "PROFISSIONAL")
    private RoleEnum role;

    public TokenJWT() {
    }

    public TokenJWT(String acessToken, Long expiresIn, RoleEnum role) {
        this.acessToken = acessToken;
        this.expiresIn = expiresIn;
        this.role = role;
    }
}