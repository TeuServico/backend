package com.teuServico.backTeuServico.appServicos.controller;

import com.teuServico.backTeuServico.appServicos.dto.OfertaServicoRequestDTO;
import com.teuServico.backTeuServico.appServicos.dto.OfertaServicoResponseDTO;
import com.teuServico.backTeuServico.appServicos.service.OfertaServicoService;
import com.teuServico.backTeuServico.shared.utils.PaginacaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ofertaservico/")
public class OfertaServicoController {

    private final OfertaServicoService ofertaServicoService;

    public OfertaServicoController(OfertaServicoService ofertaServicoService) {
        this.ofertaServicoService = ofertaServicoService;
    }

    @Operation(
            summary = "Criacão de uma oferta de servico",
            description = "Permite a um profissional(atraves de seu token de autenticação) criar uma oferta de servico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "oferta de servico criado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @PostMapping("criar")
    public OfertaServicoResponseDTO criarOfertaServico(@Valid @RequestBody OfertaServicoRequestDTO ofertaServicoRequestDTO, JwtAuthenticationToken token){
        return ofertaServicoService.criarOfertaServico(ofertaServicoRequestDTO, token);
    }

    @Operation(
            summary = "Busca todas as ofertas de servico do profissional",
            description = "Permite a um profissional(atraves de seu token de autenticação) buscar todas as suas ofertas de serviço"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @GetMapping("minhasofertas")
    public PaginacaoResponseDTO<OfertaServicoResponseDTO> minhasOfertasServico(
            @Parameter(description = "Número da página que deseja consultar", example = "1")
            @RequestParam String pagina,
            @Parameter(description = "Quantidade máxima de elementos por página", example = "10")
            String qtdMaximoElementos,
            JwtAuthenticationToken token) {
        return ofertaServicoService.minhasOfertasServico(pagina, qtdMaximoElementos, token);
    }

    @Operation(
            summary = "Busca(Containing) todas as ofertas de servico pelo nome do tipo de servico",
            description = "Retorna uma lista paginada com todas ofertas pertencentes a um nome do tipo de servico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @GetMapping("buscar/tiposervico/nome")
    public PaginacaoResponseDTO<OfertaServicoResponseDTO> buscarOfertasTipoServicoNome(
            @Parameter(description = "Número da página que deseja consultar", example = "1")
            @RequestParam String pagina,
            @Parameter(description = "Quantidade máxima de elementos por página", example = "10")
            String qtdMaximoElementos,
            @Parameter(description = "Nome que deseja filtrar", example = "Desenvolver página web")
            @RequestParam String nome) {
        return ofertaServicoService.buscarOfertasServicosPorTipoServicoNome(pagina, qtdMaximoElementos, nome);
    }

    @Operation(
            summary = "Busca todas as ofertas de servico pelo nome do tipo de servico",
            description = "Retorna uma lista paginada com todas ofertas pertencentes a uma categoria do tipo de servico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @GetMapping("buscar/tiposervico/categoria")
    public PaginacaoResponseDTO<OfertaServicoResponseDTO> buscarOfertasTipoServicoCategoria(
            @Parameter(description = "Número da página que deseja consultar", example = "1")
            @RequestParam String pagina,
            @Parameter(description = "Quantidade máxima de elementos por página", example = "10")
            String qtdMaximoElementos,
            @Parameter(description = "Categoria que deseja filtrar", example = "PROGRAMAÇÃO")
            @RequestParam String categoria) {
        return ofertaServicoService.buscarOfertasServicosPorTipoServicoCategoria(pagina, qtdMaximoElementos, categoria);
    }

}
