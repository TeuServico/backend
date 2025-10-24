package com.teuServico.backTeuServico.appServicos.controller;

import com.teuServico.backTeuServico.appServicos.dto.TipoServicoRequestDTO;
import com.teuServico.backTeuServico.appServicos.dto.TipoServicoResponseDTO;
import com.teuServico.backTeuServico.appServicos.service.TipoServicoService;
import com.teuServico.backTeuServico.shared.utils.PaginacaoResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tipoServico/")
public class TipoServicoController {
    private final TipoServicoService tipoServicoService;

    @Autowired
    public TipoServicoController(TipoServicoService tipoServicoService) {
        this.tipoServicoService = tipoServicoService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @PostMapping("criar")
    public TipoServicoResponseDTO criarTipoServico(@Valid @RequestBody TipoServicoRequestDTO tipoServicoRequestDTO){
        return tipoServicoService.criarTipoServico(tipoServicoRequestDTO);
    }

    @GetMapping("buscar/todos")
    public PaginacaoResponseDTO<TipoServicoResponseDTO> buscarTodos(@RequestParam String pagina,@RequestParam  String qtdMaximaElementos){
        return tipoServicoService.buscarTodos(pagina, qtdMaximaElementos);
    }

    @GetMapping("buscar/categoria")
    public PaginacaoResponseDTO<TipoServicoResponseDTO> buscarTodosPorCategoria( @RequestParam String categoria, @RequestParam String pagina, @RequestParam  String qtdMaximaElementos){
        return tipoServicoService.buscarTodosPorCategoria(categoria, pagina, qtdMaximaElementos);
    }

}
