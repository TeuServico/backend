package com.teuServico.backTeuServico.appServicos.controller;

import com.teuServico.backTeuServico.appServicos.dto.TipoServicoRequestDTO;
import com.teuServico.backTeuServico.appServicos.dto.TipoServicoResponseDTO;
import com.teuServico.backTeuServico.appServicos.service.TipoServicoService;
import com.teuServico.backTeuServico.shared.utils.PaginacaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tiposervico/")
public class TipoServicoController {
    private final TipoServicoService tipoServicoService;

    @Autowired
    public TipoServicoController(TipoServicoService tipoServicoService) {
        this.tipoServicoService = tipoServicoService;
    }

    @Operation(
            summary = "Criacão de um tipo de servico",
            description = "Permite a um profissional(atraves de seu token de autenticação) criar um tipo de servico que não exista"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo servico criado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/NaoAutenticado"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/NaoAutorizado"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @PostMapping("criar")
    public TipoServicoResponseDTO criarTipoServico(@Valid @RequestBody TipoServicoRequestDTO tipoServicoRequestDTO){
        return tipoServicoService.criarTipoServico(tipoServicoRequestDTO);
    }

    @Operation(
            summary = "Busca todos os tipos de serviço",
            description = "Retorna uma lista paginada com todos os tipos de serviço cadastrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @GetMapping("buscar/todos")
    public PaginacaoResponseDTO<TipoServicoResponseDTO> buscarTodos(
            @Parameter(description = "Número da página que deseja consultar", example = "1")
            @RequestParam String pagina,
            @Parameter(description = "Quantidade máxima de elementos por página", example = "10")
            @RequestParam  String qtdMaximaElementos){
        return tipoServicoService.buscarTodos(pagina, qtdMaximaElementos);
    }

    @Operation(
            summary = "Busca todos os tipos de serviço pertencentes a uma categoria",
            description = "Retorna uma lista paginada com todos os tipos de serviço pertencentes a uma categoria"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @GetMapping("buscar/categoria")
    public PaginacaoResponseDTO<TipoServicoResponseDTO> buscarTodosPorCategoria(
            @Parameter(description = "Categoria que deseja filtrar", example = "PROGRAMAÇÃO")
            @RequestParam String categoria,
            @Parameter(description = "Número da página que deseja consultar", example = "1")
            @RequestParam String pagina,
            @Parameter(description = "Quantidade máxima de elementos por página", example = "10")
            @RequestParam  String qtdMaximaElementos){
        return tipoServicoService.buscarTodosPorCategoria(categoria, pagina, qtdMaximaElementos);
    }

}
