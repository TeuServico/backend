package com.teuServico.backTeuServico.appUsuarios.controller;

import com.teuServico.backTeuServico.appUsuarios.dto.CriarProfissionalDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.ProfissionalResponseDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.TokenJWT;
import com.teuServico.backTeuServico.appUsuarios.service.ProfissionalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profissional/")
public class ProfissionalController {
    private final ProfissionalService profissionalService;

    @Autowired
    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @PostMapping("criar")
    public TokenJWT criarProfissiona(@RequestBody @Valid CriarProfissionalDTO criarProfissionalDTO){
        return profissionalService.criarUsuarioProfissional(criarProfissionalDTO.getCredenciaisUsuarioRequestDTO(), criarProfissionalDTO.getProfissionalRequestDTO());
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @GetMapping("perfil")
    public ProfissionalResponseDTO meuPerfil(JwtAuthenticationToken token){
        return profissionalService.meuPerfil(token);
    }

}
