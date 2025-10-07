package com.teuServico.backTeuServico.appUsuarios.model;

import com.teuServico.backTeuServico.appUsuarios.dto.ProfissionalRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "profissionais")
public class Profissional extends UsuarioBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String profissao;

    public Profissional(){}

    public Profissional(ProfissionalRequestDTO profissionalRequestDTO){
        super.nomeCompleto = profissionalRequestDTO.getNomeCompleto();
        super.telefone = profissionalRequestDTO.getTelefone();
        super.cpf = profissionalRequestDTO.getCpf();
        this.profissao = profissionalRequestDTO.getProfissao();
    }
}
