package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.Endereco;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

/**
 * DTO de resposta utilizado para retornar os dados de um profissional após uma consulta.
 * <p>
 * Contém informações pessoais, profissionais e de localização.
 * Os dados são extraídos (criptografados) da entidade {@link Profissional}.
 */
@Getter
@Setter
@Schema(description = "Dados retornados do profissional após consulta")
public class ProfissionalResponseDTO {

    /**
     * Identificador único do profissional.
     */
    @Schema(description = "Identificador único do profissional", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    /**
     * Email do profissional.
     */
    @Schema(description = "email do profissional", example = "rodrigosilva@gmail.com")
    private String email;

    /**
     * Nome completo do profissional.
     */
    @Schema(description = "Nome completo do profissional", example = "rodrigo da Silva")
    private String nomeCompleto;

    /**
     * Telefone do profissional.
     */
    @Schema(description = "Telefone do profissional", example = "81987654321")
    private String telefone;

    /**
     * CPF do profissional.
     */
    @Schema(description = "CPF do profissional", example = "22559547023")
    private String cpf;

    /**
     * Endereço completo do profissional.
     */
    @Schema(description = "Endereço completo do profissional")
    private Endereco endereco;

    /**
     * Descrição pessoal do profissional.
     */
    @Schema(description = "Descrição pessoal do profissional", example = "programador apaixonado por resolver problemas com código. especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. café, código e criatividade são meu combustível diário.")
    private String sobreMim;

    /**
     * Profissão do profissional.
     */
    @Schema(description = "Profissão do profissional", example = "programador")
    private String profissao;

    /**
     * Construtor padrão necessário para serialização.
     */
    public ProfissionalResponseDTO() {
    }

    /**
     * Construtor que inicializa o DTO com base na entidade {@link Profissional}.
     *
     * @param profissional entidade Profissional contendo os dados a serem retornados(é necessario os descriptografar)
     */
    public ProfissionalResponseDTO(Profissional profissional) {
        this.id = profissional.getId();
        this.email = profissional.getCredencialUsuario().getEmail();
        this.nomeCompleto = profissional.getNomeCompleto();
        this.telefone = profissional.getTelefone();
        this.cpf = profissional.getCpf();
        this.endereco = profissional.getEndereco();
        this.sobreMim = profissional.getSobreMim();
        this.profissao = profissional.getProfissao();
    }
}