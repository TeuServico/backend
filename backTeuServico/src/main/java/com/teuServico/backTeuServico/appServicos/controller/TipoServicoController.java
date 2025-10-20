package com.teuServico.backTeuServico.appServicos.controller;

import com.teuServico.backTeuServico.appServicos.dto.TipoServicoRequestDTO;
import com.teuServico.backTeuServico.appServicos.dto.TipoServicoResponseDTO;
import com.teuServico.backTeuServico.appServicos.service.TipoServicoService;
import com.teuServico.backTeuServico.shared.utils.PaginacaoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tipoServico/")
public class TipoServicoController {
    private final TipoServicoService tipoServicoService;

    @Autowired
    public TipoServicoController(TipoServicoService tipoServicoService) {
        this.tipoServicoService = tipoServicoService;
    }

    @PostMapping("criarTipoServico")
    public TipoServicoResponseDTO criarTipoServico(@RequestBody TipoServicoRequestDTO tipoServicoRequestDTO){
        return tipoServicoService.criarTipoServico(tipoServicoRequestDTO);
    }

    @GetMapping("buscarTodos")
    public PaginacaoResponseDTO<TipoServicoResponseDTO> buscarTodos(@RequestParam String pagina,@RequestParam  String qtdMaximaElementos){
        return tipoServicoService.buscarTodos(pagina, qtdMaximaElementos);
    }

    @GetMapping("buscarTodosPor/categoria")
    public PaginacaoResponseDTO<TipoServicoResponseDTO> buscarTodosPorCategoria( @RequestParam String categoria, @RequestParam String pagina, @RequestParam  String qtdMaximaElementos){
        return tipoServicoService.buscarTodosPorCategoria(categoria, pagina, qtdMaximaElementos);
    }

}
