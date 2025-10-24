package com.teuServico.backTeuServico.appUsuarios.controller;

import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.TokenJWT;
import com.teuServico.backTeuServico.appUsuarios.service.CredenciaisUsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("credenciais/")
public class CredenciaisUsuarioController {
    private final CredenciaisUsuarioService credenciaisUsuarioService;

    public CredenciaisUsuarioController(CredenciaisUsuarioService credenciaisUsuarioService) {
        this.credenciaisUsuarioService = credenciaisUsuarioService;
    }

    @PostMapping("login")
    public TokenJWT login(@RequestBody CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO){
        return credenciaisUsuarioService.login(credenciaisUsuarioRequestDTO);
    }
}
