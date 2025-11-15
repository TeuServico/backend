package com.teuServico.backTeuServico.appUsuarios.controller;

import com.teuServico.backTeuServico.appUsuarios.dto.CriarProfissionalDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.ProfissionalResponseDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.TokenJWT;
import com.teuServico.backTeuServico.appUsuarios.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável por gerenciar os endpoints de Profissional
 * <p>
 */
@RestController
@RequestMapping("profissional/")
public class ProfissionalController {
    private final ProfissionalService profissionalService;

    @Autowired
    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @Operation(
            summary = "Criação de profissional",
            description = "Cria um novo profissional e retorna o token JWT de autenticação"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional criado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @PostMapping("criar")
    public TokenJWT criarProfissiona(@RequestBody @Valid CriarProfissionalDTO criarProfissionalDTO){
        return profissionalService.criarUsuarioProfissional(criarProfissionalDTO.getCredenciaisUsuarioRequestDTO(), criarProfissionalDTO.getProfissionalRequestDTO());
    }

    @Operation(
            summary = "Perfil de profissional",
            description = "Solicita as informações pessoais de um profissional atraves de seu token de autenticação"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do profissional retornados com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @GetMapping("perfil")
    public ProfissionalResponseDTO meuPerfil(JwtAuthenticationToken token){
        return profissionalService.meuPerfil(token);
    }

}
