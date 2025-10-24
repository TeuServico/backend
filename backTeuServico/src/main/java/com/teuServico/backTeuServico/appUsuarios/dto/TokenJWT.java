package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;

@Getter
@Setter
public class TokenJWT {
    private String acessToken;
    private Long expiresIn;
    private RoleEnum role;

    public TokenJWT() {
    }

    public TokenJWT(String acessToken, Long expiresIn, RoleEnum role) {
        this.acessToken = acessToken;
        this.expiresIn = expiresIn;
        this.role = role;
    }
}
