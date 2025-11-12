package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para retornar o token JWT após a autenticação do usuário.
 * <p>
 * Contém o token de acesso, o tempo de expiração e o papel (role) do usuário autenticado.
 */
@Getter
@Setter
@Schema(description = "Token JWT retornado após autenticação")
public class TokenJWT {

    /**
     * Token JWT gerado após autenticação bem-sucedida.
     */
    @Schema(description = "Token de acesso JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String acessToken;

    /**
     * Tempo de expiração do token em segundos.
     */
    @Schema(description = "Tempo de expiração do token em segundos", example = "3600")
    private Long expiresIn;

    /**
     * Papel do usuário autenticado (CLIENTE ou PROFISSIONAL).
     */
    @Schema(description = "Papel do usuário autenticado", example = "PROFISSIONAL")
    private RoleEnum role;

    /**
     * Construtor padrão necessário para serialização.
     */
    public TokenJWT() {
    }

    /**
     * Construtor que inicializa o DTO com os dados do token JWT.
     * @param acessToken token JWT gerado
     * @param expiresIn tempo de expiração em segundos
     * @param role papel do usuário autenticado
     */
    public TokenJWT(String acessToken, Long expiresIn, RoleEnum role) {
        this.acessToken = acessToken;
        this.expiresIn = expiresIn;
        this.role = role;
    }
}