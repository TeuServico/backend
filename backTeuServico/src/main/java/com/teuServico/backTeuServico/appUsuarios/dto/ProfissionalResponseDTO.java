package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.Endereco;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class ProfissionalResponseDTO {
    private UUID id;
    private String nomeCompleto;
    private String telefone;
    private String cpf;
    private Endereco endereco;
    private String profissao;

    public ProfissionalResponseDTO() {
    }

    public ProfissionalResponseDTO(Profissional profissional) {
        this.id = profissional.getId();
        this.nomeCompleto = profissional.getNomeCompleto();
        this.telefone = profissional.getTelefone();
        this.cpf = getCpf();
        this.endereco = profissional.getEndereco();
        this.profissao = profissional.getProfissao();
    }
}