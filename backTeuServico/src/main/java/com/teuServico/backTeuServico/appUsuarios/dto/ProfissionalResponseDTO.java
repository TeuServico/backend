package com.teuservico.backTeuServico.appUsuarios.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class ProfissionalResponseDTO {
    private UUID id;
    private String nomeCompleto;
    private String email;
    private String telefone;
    private String profissao;

    public ProfissionalResponseDTO() {
    }

    public ProfissionalResponseDTO(Profissional profissional) {
        this.id = profissional.getId();
        this.nomeCompleto = profissional.getNomeCompleto();
        this.email = profissional.getCredencialUsuario().getEmail();
        this.telefone = profissional.getTelefone();
        this.profissao = profissional.getProfissao();
    }
}