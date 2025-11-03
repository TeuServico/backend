package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Dados retornados do cliente após consulta")
public class ClienteResponseDTO {

    @Schema(description = "Identificador único do cliente")
    private UUID id;

    @Schema(description = "email do cliente", example = "joaosilva@gmail.com")
    private String email;

    @Schema(description = "Nome completo do cliente", example = "joão da Silva")
    private String nomeCompleto;

    @Schema(description = "Telefone do cliente", example = "81912345678")
    private String telefone;

    @Schema(description = "CPF do cliente", example = "64479682090")
    private String cpf;

    @Schema(description = "Endereço completo do cliente")
    private Endereco endereco;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.email = cliente.getCredencialUsuario().getEmail();
        this.nomeCompleto = cliente.getNomeCompleto();
        this.telefone = cliente.getTelefone();
        this.cpf = cliente.getCpf();
        this.endereco = cliente.getEndereco();
    }
}