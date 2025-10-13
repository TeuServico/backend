package com.teuServico.backTeuServico.appUsuarios.controller;

import com.teuServico.backTeuServico.appUsuarios.dto.CriarProfissionalDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.ProfissionalResponseDTO;
import com.teuServico.backTeuServico.appUsuarios.service.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profissional/")
public class ProfissionalController {
    private final ProfissionalService profissionalService;

    @Autowired
    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @PostMapping("criarProfissional")
    public ProfissionalResponseDTO criarProfissiona(@RequestBody @Valid CriarProfissionalDTO criarProfissionalDTO){
        return profissionalService.criarUsuarioProfissional(criarProfissionalDTO.getCredenciaisUsuarioRequestDTO(), criarProfissionalDTO.getProfissionalRequestDTO());
    }
}
