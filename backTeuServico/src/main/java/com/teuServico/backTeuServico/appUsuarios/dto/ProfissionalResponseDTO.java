package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.Endereco;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Dados retornados do profissional após consulta")
public class ProfissionalResponseDTO {

    @Schema(description = "Identificador único do profissional", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "email do profissional", example = "rodrigosilva@gmail.com")
    private String email;

    @Schema(description = "Nome completo do profissional", example = "rodrigo da Silva")
    private String nomeCompleto;

    @Schema(description = "Telefone do profissional", example = "81987654321")
    private String telefone;

    @Schema(description = "CPF do profissional", example = "22559547023")
    private String cpf;

    @Schema(description = "Endereço completo do profissional")
    private Endereco endereco;

    @Schema(description = "Descrição pessoal do profissional", example = "programador apaixonado por resolver problemas com código. especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. café, código e criatividade são meu combustível diário.")
    private String sobreMim;

    @Schema(description = "Profissão do profissional", example = "programador")
    private String profissao;

    public ProfissionalResponseDTO() {
    }

    public ProfissionalResponseDTO(Profissional profissional) {
        this.id = profissional.getId();
        this.email = profissional.getCredencialUsuario().getEmail();
        this.nomeCompleto = profissional.getNomeCompleto();
        this.telefone = profissional.getTelefone();
        this.cpf = profissional.getCpf(); // Corrigido: era getCpf()
        this.endereco = profissional.getEndereco();
        this.sobreMim = profissional.getSobreMim();
        this.profissao = profissional.getProfissao();
    }
}