package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

/**
 * DTO de resposta utilizado para retornar os dados de um cliente após uma consulta.
 * <p>
 * Contém informações pessoais e de contato, além do endereço completo.
 * Os dados são extraídos(criptografados) da entidade {@link Cliente}
 */
@Getter
@Setter
@Schema(description = "Dados retornados do cliente após consulta")
public class ClienteResponseDTO {

    /**
     * Identificador único do cliente.
     */
    @Schema(description = "Identificador único do cliente")
    private UUID id;

    /**
     * E-mail do cliente utilizado para autenticação.
     */
    @Schema(description = "email do cliente", example = "joaosilva@gmail.com")
    private String email;

    /**
     * Nome completo do cliente.
     */
    @Schema(description = "Nome completo do cliente", example = "joão da Silva")
    private String nomeCompleto;

    /**
     * Número de telefone do cliente.
     */
    @Schema(description = "Telefone do cliente", example = "81912345678")
    private String telefone;

    /**
     * CPF do cliente.
     */
    @Schema(description = "CPF do cliente", example = "64479682090")
    private String cpf;

    /**
     * Endereço completo do cliente.
     */
    @Schema(description = "Endereço completo do cliente")
    private Endereco endereco;

    /**
     * Construtor padrão necessário para serialização.
     */
    public ClienteResponseDTO() {
    }

    /**
     * Construtor que inicializa o DTO com base na entidade {@link Cliente}.
     *
     * @param cliente entidade Cliente contendo os dados a serem retornados(é necessario os descriptografar)
     */
    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.email = cliente.getCredencialUsuario().getEmail();
        this.nomeCompleto = cliente.getNomeCompleto();
        this.telefone = cliente.getTelefone();
        this.cpf = cliente.getCpf();
        this.endereco = cliente.getEndereco();
    }
}