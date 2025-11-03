package com.teuServico.backTeuServico.agendamento.controller;

import com.teuServico.backTeuServico.agendamento.dto.AgendamentoRequestDTO;
import com.teuServico.backTeuServico.agendamento.dto.AgendamentoResponseDTO;
import com.teuServico.backTeuServico.agendamento.service.AgendamentoService;
import com.teuServico.backTeuServico.shared.utils.PaginacaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("agendamento/")
public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @Operation(
            summary = "Solicitaçao de um agendamento",
            description = "Permite a um cliente(atraves de seu token de autenticação) solicitar o agendamento de uma oferta de servico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento solicitado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @PostMapping("solicitar")
    public AgendamentoResponseDTO solicitarAgendamento(@Valid @RequestBody AgendamentoRequestDTO agendamentoRequestDTO, JwtAuthenticationToken token){
        return agendamentoService.solicitarAgendamento(agendamentoRequestDTO, token);
    }

    @Operation(
            summary = "Busca todos os agendamentos de um cliente",
            description = "Permite a um cliente(atraves de seu token de autenticação) obter todos os seus agendamentos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento solicitado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @GetMapping("meusagendamentos")
    public PaginacaoResponseDTO<AgendamentoResponseDTO> meusAgendamentosCliente(String pagina, String qtdMaximaElementos, JwtAuthenticationToken token){
        return agendamentoService.meusAgendamentosCliente(pagina, qtdMaximaElementos, token);
    }

    @Operation(
            summary = "Busca todos os agendamentos de um profissional",
            description = "Permite a um profissional(atraves de seu token de autenticação) obter todos os seus agendamentos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento solicitado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @GetMapping("meusagendamentos")
    public PaginacaoResponseDTO<AgendamentoResponseDTO> meusAgendamentosProfissional(String pagina, String qtdMaximaElementos, JwtAuthenticationToken token){
        return agendamentoService.meusAgendamentosProfissional(pagina, qtdMaximaElementos, token);
    }

}
