package com.teuServico.backTeuServico.agendamento.controller;

import com.teuServico.backTeuServico.agendamento.dto.AgendamentoRequestDTO;
import com.teuServico.backTeuServico.agendamento.dto.AgendamentoResponseDTO;
import com.teuServico.backTeuServico.agendamento.dto.ContraOfertaRequestDTO;
import com.teuServico.backTeuServico.agendamento.service.AgendamentoService;
import com.teuServico.backTeuServico.shared.utils.PaginacaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável por gerenciar os endpoints de Agendamento
 * <p>
 */
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
    @PostMapping("cliente/solicitar")
    public AgendamentoResponseDTO solicitarAgendamento(@Valid @RequestBody AgendamentoRequestDTO agendamentoRequestDTO, JwtAuthenticationToken token){
        return agendamentoService.solicitarAgendamento(agendamentoRequestDTO, token);
    }

    @Operation(
            summary = "Busca todos os agendamentos de um cliente",
            description = "Permite a um cliente(atraves de seu token de autenticação) obter todos os seus agendamentos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @GetMapping("cliente/meusagendamentos")
    public PaginacaoResponseDTO<AgendamentoResponseDTO> meusAgendamentosCliente(
            @Parameter(description = "Número da página que deseja consultar", example = "1")
            @RequestParam String pagina,
            @Parameter(description = "Quantidade máxima de elementos por página", example = "10")
            @RequestParam String qtdMaximaElementos,
            JwtAuthenticationToken token
    ){
        return agendamentoService.meusAgendamentosCliente(pagina, qtdMaximaElementos, token);
    }

    @Operation(
            summary = "Busca todos os agendamentos de um profissional",
            description = "Permite a um profissional(atraves de seu token de autenticação) obter todos os seus agendamentos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @GetMapping("profissional/meusagendamentos")
    public PaginacaoResponseDTO<AgendamentoResponseDTO> meusAgendamentosProfissional(
            @Parameter(description = "Número da página que deseja consultar", example = "1")
            @RequestParam String pagina,
            @Parameter(description = "Quantidade máxima de elementos por página", example = "10")
            @RequestParam String qtdMaximaElementos,
            JwtAuthenticationToken token){
        return agendamentoService.meusAgendamentosProfissional(pagina, qtdMaximaElementos, token);
    }

    @Operation(
            summary = "Aceitar agendamento solicitado pelo cliente",
            description = "Permite a um profissional(atraves de seu token de autenticação) aceitar o agendamento feito por um cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento aceito com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @PostMapping("profissional/aceitar")
    public ResponseEntity<String> profissionalAceitarAgendamento(@RequestParam String idAgendamento, JwtAuthenticationToken token){
        return agendamentoService.profissionalAceitarAgendamento(idAgendamento, token);
    }

    @Operation(
            summary = "Faz uma contra oferta de agendamento",
            description = "Permite a um profissional(atraves de seu token de autenticação) fazer uma contra oferta para um agendamento feito por um cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contra oferta de agendamento feita com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @PostMapping("profissional/fazer/contraoferta")
    public ResponseEntity<String> profissionalFazerContraOferta(@RequestBody @Valid ContraOfertaRequestDTO contraOfertaRequestDTO, JwtAuthenticationToken token){
        return agendamentoService.profissionalFazerContraOferta(contraOfertaRequestDTO, token);
    }

    @Operation(
            summary = "Aceitar contra oferta de agendamento solicitada pelo profissional",
            description = "Permite a um cliente(atraves de seu token de autenticação) aceitar o a contra oferta de agendamento feita por um profissional"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contra oferta de agendamento aceita com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @PostMapping("cliente/aceitar/contraoferta")
    public ResponseEntity<String> clienteAceitarContraOferta(@RequestParam String idAgendamento, JwtAuthenticationToken token){
        return agendamentoService.clienteAceitarAgendamentoContraOferta(idAgendamento, token);
    }

    @Operation(
            summary = "Cliente cancela agendamento",
            description = "Permite a um cliente(atraves de seu token de autenticação) cancelar um agendamento"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @PostMapping("cliente/cancelar")
    public ResponseEntity<String> clienteCancelarAgendamento(@RequestParam String idAgendamento, JwtAuthenticationToken token){
        return agendamentoService.clienteCancelarAgendamento(idAgendamento, token);
    }

    @Operation(
            summary = "Profissional cancela agendamento",
            description = "Permite a um profissional(atraves de seu token de autenticação) cancelar um agendamento"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @PostMapping("profissional/cancelar")
    public ResponseEntity<String> profissionalCancelarAgendamento(@RequestParam String idAgendamento, JwtAuthenticationToken token){
        return agendamentoService.profissionalCancelarAgendamento(idAgendamento, token);
    }

    @Operation(
            summary = "Profissional conclui o agendamento",
            description = "Permite a um profissional(atraves de seu token de autenticação) concluir o agendamento"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento concluido com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @PostMapping("profissional/concluir")
    public ResponseEntity<String> profissionalConcluirAgendamento(@RequestParam String idAgendamento, JwtAuthenticationToken token){
        return agendamentoService.profissionalConcluirAgendamento(idAgendamento, token);
    }

}
