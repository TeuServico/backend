package com.teuServico.backTeuServico.appUsuarios.controller;

import com.teuServico.backTeuServico.appUsuarios.dto.ClienteResponseDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.CriarClienteDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.TokenJWT;
import com.teuServico.backTeuServico.appUsuarios.service.ClienteService;
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
 * Controller responsável por gerenciar os endpoints de Cliente
 * <p>
 */
@RestController
@RequestMapping("cliente/")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(
            summary = "Criação de cliente",
            description = "Cria um novo cliente e retorna o token JWT de autenticação"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @PostMapping("criar")
    public TokenJWT criarCliente(@RequestBody @Valid CriarClienteDTO criarClienteDTO){
        return clienteService.criarUsuarioCliente(criarClienteDTO.getCredenciaisUsuarioRequestDTO(), criarClienteDTO.getClienteRequestDTO());
    }

    @Operation(
            summary = "Perfil de cliente",
            description = "Solicita as informações pessoais de um cliente atraves de seu token de autenticação"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do cliente retornados com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @GetMapping("perfil")
    public ClienteResponseDTO meuPerfil(JwtAuthenticationToken token){
        return clienteService.meuPerfil(token);
    }
}
